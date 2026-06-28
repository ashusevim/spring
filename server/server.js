const express = require('express');
const cors = require('cors');
const { execSync } = require('child_process');
const fs = require('fs');
const path = require('path');
const xml2js = require('xml2js');

const app = express();
app.use(cors());
app.use(express.json());

// Serve dashboard
app.use(express.static(path.join(__dirname, '..', 'dashboard')));

const CHALLENGES_DIR = path.join(__dirname, '..', 'challenges');

// List all challenges
app.get('/api/challenges', (req, res) => {
    const dirs = fs.readdirSync(CHALLENGES_DIR).filter(d =>
        fs.statSync(path.join(CHALLENGES_DIR, d)).isDirectory()
    ).sort();

    const challenges = dirs.map(dir => {
        const metaPath = path.join(CHALLENGES_DIR, dir, 'challenge.json');
        if (fs.existsSync(metaPath)) {
            return JSON.parse(fs.readFileSync(metaPath, 'utf8'));
        }
        return null;
    }).filter(Boolean);

    res.json(challenges);
});

// Get challenge details
app.get('/api/challenges/:id', (req, res) => {
    const dirs = fs.readdirSync(CHALLENGES_DIR).sort();
    const dir = dirs.find(d => {
        const metaPath = path.join(CHALLENGES_DIR, d, 'challenge.json');
        if (fs.existsSync(metaPath)) {
            const meta = JSON.parse(fs.readFileSync(metaPath, 'utf8'));
            return meta.id === req.params.id;
        }
        return false;
    });

    if (!dir) return res.status(404).json({ error: 'Challenge not found' });

    const meta = JSON.parse(fs.readFileSync(path.join(CHALLENGES_DIR, dir, 'challenge.json'), 'utf8'));

    // Read skeleton files to show
    const skeletonFiles = [];
    const srcMain = path.join(CHALLENGES_DIR, dir, 'src', 'main', 'java', 'com', 'springarena');
    if (fs.existsSync(srcMain)) {
        readJavaFiles(srcMain, skeletonFiles, srcMain);
    }

    // Read test files
    const testFiles = [];
    const srcTest = path.join(CHALLENGES_DIR, dir, 'src', 'test', 'java', 'com', 'springarena');
    if (fs.existsSync(srcTest)) {
        readJavaFiles(srcTest, testFiles, srcTest);
    }

    res.json({ ...meta, skeletonFiles, testFiles });
});

function readJavaFiles(dir, files, baseDir) {
    for (const entry of fs.readdirSync(dir, { withFileTypes: true })) {
        const fullPath = path.join(dir, entry.name);
        if (entry.isDirectory()) {
            readJavaFiles(fullPath, files, baseDir);
        } else if (entry.name.endsWith('.java')) {
            files.push({
                path: path.relative(baseDir, fullPath),
                content: fs.readFileSync(fullPath, 'utf8')
            });
        }
    }
}

// Grade a challenge
app.post('/api/grade/:id', async (req, res) => {
    const dirs = fs.readdirSync(CHALLENGES_DIR).sort();
    const dir = dirs.find(d => {
        const metaPath = path.join(CHALLENGES_DIR, d, 'challenge.json');
        if (fs.existsSync(metaPath)) {
            const meta = JSON.parse(fs.readFileSync(metaPath, 'utf8'));
            return meta.id === req.params.id;
        }
        return false;
    });

    if (!dir) return res.status(404).json({ error: 'Challenge not found' });

    const challengeDir = path.join(CHALLENGES_DIR, dir);

    try {
        // Run mvn test
        try {
            execSync('mvn test -q 2>&1', { cwd: challengeDir, timeout: 120000 });
        } catch (e) {
            // Maven returns non-zero on test failures — that's expected
        }

        // Parse surefire reports
        const reportsDir = path.join(challengeDir, 'target', 'surefire-reports');
        if (!fs.existsSync(reportsDir)) {
            return res.json({
                success: false,
                error: 'Build failed — no test reports generated. Check your code compiles.',
                tests: [],
                passed: 0,
                total: 0
            });
        }

        const xmlFiles = fs.readdirSync(reportsDir).filter(f => f.endsWith('.xml'));
        const allTests = [];

        for (const xmlFile of xmlFiles) {
            const xml = fs.readFileSync(path.join(reportsDir, xmlFile), 'utf8');
            const result = await xml2js.parseStringPromise(xml);
            const suite = result.testsuite;

            if (suite && suite.testcase) {
                for (const tc of suite.testcase) {
                    const test = {
                        name: tc.$.name,
                        className: tc.$.classname,
                        time: parseFloat(tc.$.time || '0'),
                        status: 'PASSED'
                    };

                    if (tc.failure) {
                        test.status = 'FAILED';
                        test.message = tc.failure[0].$.message || '';
                        test.detail = typeof tc.failure[0] === 'string' ? tc.failure[0] : (tc.failure[0]._ || '');
                    } else if (tc.error) {
                        test.status = 'ERROR';
                        test.message = tc.error[0].$.message || '';
                        test.detail = typeof tc.error[0] === 'string' ? tc.error[0] : (tc.error[0]._ || '');
                    } else if (tc.skipped) {
                        test.status = 'SKIPPED';
                    }

                    allTests.push(test);
                }
            }
        }

        const passed = allTests.filter(t => t.status === 'PASSED').length;
        res.json({
            success: passed === allTests.length,
            tests: allTests,
            passed,
            total: allTests.length
        });

    } catch (err) {
        res.json({
            success: false,
            error: err.message,
            tests: [],
            passed: 0,
            total: 0
        });
    }
});

const PORT = process.env.PORT || 3001;
app.listen(PORT, () => {
    console.log(`\n🏋️  Spring Boot Practice Arena`);
    console.log(`   Dashboard: http://localhost:${PORT}`);
    console.log(`   API:       http://localhost:${PORT}/api/challenges\n`);
});
