#!/bin/bash

# Maven wrapper script that sets JAVA_HOME to IntelliJ's bundled JDK
# Usage: ./mvn.sh clean compile test package install

export JAVA_HOME="/Applications/IntelliJ IDEA.app/Contents/jbr/Contents/Home"
"/Applications/IntelliJ IDEA.app/Contents/plugins/maven/lib/maven3/bin/mvn" "$@"
