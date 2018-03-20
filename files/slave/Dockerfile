FROM ubuntu:xenial
MAINTAINER Fedor Patrakov <freddy-good@yandex.ru>

ENV USER={{ jenkins_user }}
ENV GROUP={{ jenkins_group }}
ENV UID={{ jenkins_uid }}
ENV GID={{ jenkins_gid }}
ENV JENKINS_AGENT_HOME={{ jenkins_agent_home }}
ENV JENKINS_SLAVE_SSH_PUBKEY="{{ jenkins_agent_key }}"

RUN groupadd -g ${GID} ${GROUP} \
    && useradd -d "${JENKINS_AGENT_HOME}" -u "${UID}" -g "${GID}" -m -s /bin/bash "${USER}"

RUN apt-get update \
    && apt-get install -y curl openjdk-8-jdk \
    && rm -rf /var/lib/apt/lists/*

RUN mkdir -p /opt/jenkins

RUN apt-get update \
    && apt-get install --no-install-recommends -y openssh-server \
    && rm -rf /var/lib/apt/lists/*
RUN sed -i 's/#PermitRootLogin.*/PermitRootLogin no/' /etc/ssh/sshd_config
RUN sed -i 's/#RSAAuthentication.*/RSAAuthentication yes/' /etc/ssh/sshd_config
RUN sed -i 's/#PasswordAuthentication.*/PasswordAuthentication no/' /etc/ssh/sshd_config
RUN sed -i 's/#SyslogFacility.*/SyslogFacility AUTH/' /etc/ssh/sshd_config
RUN sed -i 's/#LogLevel.*/LogLevel INFO/' /etc/ssh/sshd_config
RUN mkdir /var/run/sshd

COPY setup-sshd.sh /opt/jenkins/setup-sshd.sh

WORKDIR "${JENKINS_AGENT_HOME}"

VOLUME "${JENKINS_AGENT_HOME}"

EXPOSE 22

ENTRYPOINT ["bash", "/opt/jenkins/setup-sshd.sh"]
