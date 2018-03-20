#! /bin/bash -e

JENKINS_WAR=${JENKINS_WAR:-'/opt/jenkins/jenkins.war'}
JENKINS_HOME=${JENKINS_HOME:-'/var/lib/jenkins'}

mkdir ${JENKINS_HOME}/plugins
while read arg; do
	cp -v $arg ${JENKINS_HOME}/plugins/
done < <(find /opt/jenkins/plugins/ \( -type f -o -type l \))

exec java $JAVA_OPTS -Duser.home=${JENKINS_HOME} -jar ${JENKINS_WAR} ${JENKINS_OPTS} "$@"
