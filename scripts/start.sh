#!/bin/bash
# Spring Arena — Start Script
# Starts the grading server and opens the dashboard

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT_DIR="$(dirname "$SCRIPT_DIR")"

echo ""
echo "  🍃 Spring Boot Practice Arena"
echo "  ================================"
echo ""

# Install server dependencies if needed
if [ ! -d "$ROOT_DIR/server/node_modules" ]; then
    echo "  📦 Installing server dependencies..."
    cd "$ROOT_DIR/server" && npm install --silent
    echo "  ✅ Dependencies installed"
fi

# Pre-download Maven dependencies for first challenge
echo "  📥 Pre-downloading Spring Boot dependencies (first run may take a minute)..."
cd "$ROOT_DIR/challenges/01-basic-crud" && mvn dependency:resolve -q 2>/dev/null || true

echo ""
echo "  🚀 Starting grading server..."
echo ""

cd "$ROOT_DIR/server" && node server.js
