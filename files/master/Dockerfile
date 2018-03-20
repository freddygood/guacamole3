FROM ubuntu:xenial
MAINTAINER Fedor Patrakov <freddy-good@yandex.ru>

ENV USER={{ jenkins_user }}
ENV GROUP={{ jenkins_group }}
ENV UID={{ jenkins_uid }}
ENV GID={{ jenkins_gid }}
ENV JENKINS_HOME={{ jenkins_home }}
ENV JENKINS_PORT={{ jenkins_port }}
ENV JENKINS_VERSION={{ jenkins_version }}
ENV JENKINS_URL={{ jenkins_url }}
ENV JENKINS_UC https://updates.jenkins.io
ENV JENKINS_UC_EXPERIMENTAL=https://updates.jenkins.io/experimental
ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"
ENV JENKINS_OPTS="--httpPort=${JENKINS_PORT}"

RUN groupadd -g ${GID} ${GROUP} \
    && useradd -d "${JENKINS_HOME}" -u "${UID}" -g "${GID}" -m -s /bin/bash "${USER}"

RUN apt-get update \
    && apt-get install -y curl openjdk-8-jdk unzip \
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
