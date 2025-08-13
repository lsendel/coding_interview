# Maven Build Solution

## Issue Fixed ✅

The Maven error you encountered:
```
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-install-plugin:3.1.2:install (default-cli) on project coding-interview: The packaging plugin for project coding-interview did not assign a file to the build artifact
```

**Root Cause**: You were running `mvn install` directly without first building the project. Maven couldn't install a JAR that didn't exist yet.

**Additional Issue**: Maven couldn't find Java runtime because JAVA_HOME wasn't set properly.

## Solution

### Option 1: Use the Maven Wrapper Script (Recommended)
I've created a `mvn.sh` script that automatically sets the correct JAVA_HOME:

```bash
# Make sure you're in the project directory
cd /Users/lsendel/IdeaProjects/coding_interview

# Use the wrapper for any Maven command
./mvn.sh clean compile test package install
./mvn.sh test
./mvn.sh package
./mvn.sh --version
```

### Option 2: Manual Commands with JAVA_HOME
If you prefer to run Maven directly:

```bash
# Set JAVA_HOME and run Maven commands
export JAVA_HOME="/Applications/IntelliJ IDEA.app/Contents/jbr/Contents/Home"

# Full build lifecycle
"/Applications/IntelliJ IDEA.app/Contents/plugins/maven/lib/maven3/bin/mvn" clean compile test package install

# Individual phases
"/Applications/IntelliJ IDEA.app/Contents/plugins/maven/lib/maven3/bin/mvn" compile
"/Applications/IntelliJ IDEA.app/Contents/plugins/maven/lib/maven3/bin/mvn" test
"/Applications/IntelliJ IDEA.app/Contents/plugins/maven/lib/maven3/bin/mvn" package
```

## Maven Lifecycle Phases (In Order)

1. **clean** - Removes target directory
2. **compile** - Compiles source code
3. **test** - Runs unit tests
4. **package** - Creates JAR file
5. **install** - Installs JAR to local Maven repository

## Verification Results ✅

```bash
$ ./mvn.sh clean compile test package install
[INFO] BUILD SUCCESS
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
[INFO] Installing .../coding-interview-1.0-SNAPSHOT.jar to ~/.m2/repository/...
```

**Created Artifacts:**
- `target/coding-interview-1.0-SNAPSHOT.jar` (156 KB)
- Installed to local Maven repository: `~/.m2/repository/com/example/coding-interview/1.0-SNAPSHOT/`

**Test Results:**
- ✅ All tests pass
- ✅ JAR file created successfully
- ✅ Main class runs: `java -cp target/coding-interview-1.0-SNAPSHOT.jar com.example.Main`

## IntelliJ IDEA Integration

Since you're using IntelliJ IDEA, you can also:

1. **Use IntelliJ's Maven Tool Window**:
   - View → Tool Windows → Maven
   - Run lifecycle phases directly from the GUI

2. **Configure IntelliJ's Maven Settings**:
   - File → Settings → Build, Execution, Deployment → Build Tools → Maven
   - Set Maven home directory to: `/Applications/IntelliJ IDEA.app/Contents/plugins/maven/lib/maven3`
   - Set JDK to: `/Applications/IntelliJ IDEA.app/Contents/jbr/Contents/Home`

## Quick Commands Reference

```bash
# Clean build
./mvn.sh clean compile

# Run tests
./mvn.sh test

# Create JAR
./mvn.sh package

# Full build and install
./mvn.sh clean install

# Run specific algorithm
java -cp target/classes com.demo.array.MaxSubArray

# Run from JAR
java -cp target/coding-interview-1.0-SNAPSHOT.jar com.example.Main
```

Your project is now fully working with Maven! 🎉
