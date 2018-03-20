# guacamole3

### prerequisites

Requirements:

- ansible >= 2.4
- vagrant
- virtualbox

Completing the task I used ansible-galaxy roles `geerlingguy.docker` and `geerlingguy.pip` and some scripts from the repo https://github.com/jenkinsci/docker

All containers are started inside a vagrant virtual machine.
All jenkins data are consistent and are outside containers.
Slaves are connected over SSH.

What the script does:

- configure parameters in inventory - such as port, version, username, etc
- prepare vagrant machine - install python and docker
- create images from Dockerfiles for master and slave nodes
- create network and volumes
- run one master and two slave nodes
- update admin password
- install required plugins
- create SSH credentials for slaves
- install public keys into slaves
- register and launch slaves via master
- display connecting information after all tasks done

As a bonus there is a docker-compose manifest. It has its own files that replace ansible templates and inventory.

### directory layout

```|- docker-compose   - manifests for docker-compose, it must be run from this directory
|- files            - all files for images building
|  |- master
|   \ slave
|
|- inventory        - script for discovery of connection parameters of vagrant machine
|   \ group_vars    - variables
|- roles            - roles for pip and docker installation
|- tasks            - playbooks for prepare, create, clean tasks
|
|- Makefile         - make based tasks
|- requirements.yml - requirements for local roles installation
|- Vagrantfile      - manifest for vagrant machine```

### checkout and run scripts

All command are started from the root directory of the repo:

```git clone git@github.com:freddygood/guacamole3.git
cd guacamole3```

### prepare virtual server

```vagrant up
make prepare```

Those tasks perform the following:

- spin up vagrant machine
- install python and pip
- install docker

### create and run jenkins containers

```make create```

This task performs the following:

- creates 2 docker images inside a vargant machine
- create docker volumes
- create docker network
- spin up docker containers

These tasks are started after the master starts:

- update admin password
- install required plugins
- create SSH credentials for slaves
- register and launch slaves

### delete all created resource

```make clean```

This task stop and destroy containers, images, network and volumes.

### prune all unused docker resources

```make prune```

### bonus

All docker compose related files are in a separate directory because compose doesn't support templating and inventory.
To build and spin up containers with compose run the command:

```cd docker-compose
docker-compose up```

### good to be done

- pull ssh keys from images
- add configurable parameters - such as slave number, java_opt, etc
- split create task to shorter tasks - such as create_images, run master, run slave, etc
- rewrite jenkins startup scripts and make them more convenient
- catch containers logs with rsyslog
- use external docker registery for posible sclability
