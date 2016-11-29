#!/bin/sh
# ----------------------------------------------------------------------------
# Launcher for Spring Boot applications
# 
# This script adds the configuration directory to the Spring Boot "classpath"
# (loader path) before launching the executable (uber) jar as a background job.
# See : http://docs.spring.io/spring-boot/docs/current/reference/html/executable-jar.html#executable-jar-launching
#
# The standard DEP project directory structured is required :
#     
#     <application name>
#     |
#     +-conf
#     |  +-application.properties
#     |  +-<config files needed in classpath>
#     |
#     +-lib
#     |  +-<application name>-<application version>.jar
#     |
#     +-bin
#        +-<application name>.sh (this script)
#  
# ----------------------------------------------------------------------------

# Reading program base directory
# resolve links - $0 may be a softlink
PRG="$0"

while [ -h "$PRG" ]; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done

PRGDIR=`dirname "$PRG"`
BASEDIR=`cd "$PRGDIR/.." >/dev/null; pwd`

# OS specific support.  $var _must_ be set to either true or false.
cygwin=false;
darwin=false;
case "`uname`" in
  CYGWIN*) cygwin=true ;;
  Darwin*) darwin=true
           if [ -z "$JAVA_VERSION" ] ; then
             JAVA_VERSION="CurrentJDK"
           else
             echo "Using Java version: $JAVA_VERSION"
           fi
		   if [ -z "$JAVA_HOME" ]; then
		      if [ -x "/usr/libexec/java_home" ]; then
			      JAVA_HOME=`/usr/libexec/java_home`
			  else
			      JAVA_HOME=/System/Library/Frameworks/JavaVM.framework/Versions/${JAVA_VERSION}/Home
			  fi
           fi       
           ;;
esac

if [ -z "$JAVA_HOME" ] ; then
  if [ -r /etc/gentoo-release ] ; then
    JAVA_HOME=`java-config --jre-home`
  fi
fi

# For Cygwin, ensure paths are in UNIX format before anything is touched
if $cygwin ; then
  [ -n "$JAVA_HOME" ] && JAVA_HOME=`cygpath --unix "$JAVA_HOME"`
fi

# If a specific java binary isn't specified search for the standard 'java' binary
if [ -z "$JAVACMD" ] ; then
  if [ -n "$JAVA_HOME"  ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
      # IBM's JDK on AIX uses strange locations for the executables
      JAVACMD="$JAVA_HOME/jre/sh/java"
    else
      JAVACMD="$JAVA_HOME/bin/java"
    fi
  else
    JAVACMD=`which java`
  fi
fi

if [ ! -x "$JAVACMD" ] ; then
  echo "Error: JAVA_HOME is not defined correctly." 1>&2
  echo "  We cannot execute $JAVACMD" 1>&2
  exit 1
fi

if [ -z "$REPO" ]
then
  REPO="$BASEDIR"/lib
fi

# For Cygwin, switch paths to Windows format before running java
if $cygwin; then
  [ -n "$JAVA_HOME" ] && JAVA_HOME=`cygpath --path --windows "$JAVA_HOME"`
  [ -n "$HOME" ] && HOME=`cygpath --path --windows "$HOME"`
  [ -n "$BASEDIR" ] && BASEDIR=`cygpath --path --windows "$BASEDIR"`
  [ -n "$REPO" ] && REPO=`cygpath --path --windows "$REPO"`
fi

APP_JAR_PATH="$REPO/${project.artifactId}-${project.version}.${project.packaging}"
APP_CONF_PATH="$BASEDIR/conf/"

# Prog PID file
PROG_PID_FILE_PATH=$BASEDIR/bin/progId.pid

start() {
	echo \"Starting $SYSTEME...\"
	status
	if [ $? -eq 0 ]; then
		echo "${project.name} (pid $RUNNING_PID) is already running. Aborting."
		exit 100
	fi
	
	# loader.path param adds the conf directory to the "classpath"
	nohup "$JAVACMD" \
	  -Dloader.path="$APP_CONF_PATH,$APP_JAR_PATH" \
	  -jar "$APP_JAR_PATH" \
	  -Dapp.name="${project.name}" \
	  -Dapp.pid="$$" \
	  -Dapp.repo="$REPO" \
	  -Dapp.home="$BASEDIR" \
	  -Dbasedir="$BASEDIR" \
	  "$@" \
	  >/dev/null 2>&1 &
	 
	 # Create pid file
	 echo $! > $PROG_PID_FILE_PATH
	 
	sleep 1
	status
	if [ $? -ne 0 ]; then
		failure ${project.name} failed to start. please check the logs.
		echo
		exit 1
	else 
		echo ${project.name} started.
		echo
		#exit 0
		return 0
	fi
}

stop() {
	echo "Stopping ${project.name}...  "
	status
   
   if [ $? -ne 0 ]; then 
   	echo ${project.name} is not running. Aborting.
   else
   	kill `cat $PROG_PID_FILE_PATH` && rm $PROG_PID_FILE_PATH
   	# checking status
   	status
   	if [ $? -eq 0 ]; then
		echo ${project.name} failed to stop. please check the logs."
		echo
		exit 1
	else 
		echo ${project.name} stopped."
		echo
		#exit 0
    	return 0 
	fi
   fi
}

status() {
	RUNNING_PID=0
	if [ -f $PROG_PID_FILE_PATH ]; then
		TMP_PID=`cat $PROG_PID_FILE_PATH`
		TMP_PID_CHECK=`ps -p $TMP_PID -o pid=`
		if [ "$TMP_PID_CHECK" != "" ]; then
			RUNNING_PID=$TMP_PID
			return 0  # running
		else
			return 1  # stopped, but pid file exists
		fi
	fi
	
	return 3 # stopped
}

case "$1" in 
start)
	start
	;;
stop)
	stop
   	;;
restart)
   	stop
   	start
   	;;
status)
   	status
   	
	RET=$?
	if [ $RET -eq 0 ]; then
		echo "${project.name} (pid $RUNNING_PID) is running..."
	elif [ $RET -eq 1 ]; then
		echo "${project.name} is dead but pidfile ($PROG_PID_FILE_PATH) exists..."
	else
		echo "${project.name} is stopped."
	fi
	exit $RET
   	;;
*)
   echo "Usage: $0 {start|stop|status|restart}"
esac

exit 0 
