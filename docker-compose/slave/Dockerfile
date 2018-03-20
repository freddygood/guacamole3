FROM ubuntu:xenial
MAINTAINER Fedor Patrakov <freddy-good@yandex.ru>

ENV USER=jenkins
ENV GROUP=jenkins
ENV UID=1000
ENV GID=1000
ENV JENKINS_AGENT_HOME=/var/lib/jenkins
ENV JENKINS_SLAVE_SSH_PUBKEY="ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQC7TXXfwuGN7B2fVAR2BYSs4MEZ4lRDilueY9wqaxxttkYWF/zot2O6AdEX99tW7w4NNjEh6m3TR5k45LYeB9q8ECWVrQXR6FagRB2MKJAVEUGCZs8MCYTNLKQVKyWdTrPa8rsJ4dTVLCw7MOp6uioQILvBG1hADb4IZ9qncKXKavCNN5ALpchtGE3f8WlVPjzt/leqDguyU4CwSzVCJbyGyW0ouOOYJUkNxoxFigU9BhP+m6nzoLI/CKkKpmLGU36LdO2u1zH21NUp4nrfUdyNlJRbHXFTp+ibNf6vN0YM0xnhg+SEsdFfRhPLnScqLguRBMqOfp3kX1V1Jvt0hpj3"

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
