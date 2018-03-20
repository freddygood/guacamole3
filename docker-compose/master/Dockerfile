FROM ubuntu:xenial
MAINTAINER Fedor Patrakov <freddy-good@yandex.ru>

ENV USER=jenkins
ENV GROUP=jenkins
ENV UID=1000
ENV GID=1000
ENV JENKINS_HOME=/var/lib/jenkins
ENV JENKINS_PORT=8181
ENV JENKINS_VERSION=2.107.1
ENV JENKINS_URL=https://repo.jenkins-ci.org/public/org/jenkins-ci/main/jenkins-war/${JENKINS_VERSION}/jenkins-war-${JENKINS_VERSION}.war
ENV JENKINS_UC https://updates.jenkins.io
ENV JENKINS_UC_EXPERIMENTAL=https://updates.jenkins.io/experimental
ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"
ENV JENKINS_OPTS="--httpPort=${JENKINS_PORT}"

RUN groupadd -g ${GID} ${GROUP} \
    && useradd -d "${JENKINS_HOME}" -u "${UID}" -g "${GID}" -m -s /bin/bash "${USER}"

RUN apt-get update \
    && apt-get install -y curl openjdk-8-jdk unzip openssh-client \
    && rm -rf /var/lib/apt/lists/*

RUN mkdir -p /opt/jenkins/plugins
RUN chown ${USER}:${GROUP} /opt/jenkins/plugins

RUN curl -fsSL ${JENKINS_URL} -o /opt/jenkins/jenkins.war

COPY jenkins.sh /opt/jenkins/
COPY jenkins-support /opt/jenkins/
COPY install-plugins.sh /opt/jenkins/
COPY plugins_init /opt/jenkins/

USER ${USER}

WORKDIR "${JENKINS_HOME}"

RUN mkdir -m 700 .ssh
COPY slave_rsa .ssh/

RUN mkdir init.groovy.d
COPY *.groovy init.groovy.d/

RUN bash /opt/jenkins/install-plugins.sh < /opt/jenkins/plugins_init

VOLUME "${JENKINS_HOME}"

EXPOSE ${JENKINS_PORT}

ENTRYPOINT ["bash", "/opt/jenkins/jenkins.sh"]
