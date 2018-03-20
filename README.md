# guacamole3

### Prerequisites

Requirements:

- ansible >= 2.4
- vagrant
- virtualbox

Completing the task I used ansible-galaxy roles `geerlingguy.docker` and `geerlingguy.pip` and some scripts from the repo https://github.com/jenkinsci/docker

All images are built by ansible and based on ubuntu xenial as the requirement was not to use pre-built image
All containers are started inside a vagrant virtual machine.
All jenkins data are consistent and are outside containers.
Slaves are connected over SSH.

What the script does:

- configure parameters in inventory - such as port, version, username, etc
- prepare vagrant machine - install python and docker
- create images from Dockerfiles for master and slave nodes
- create network and volumes
- run one master and two slave nodes
- update admin password as required
- install required plugins
- create SSH credentials for slaves
- install public keys into slaves
- register and launch slaves via master
- display connecting information after all tasks done

As a bonus there is a docker-compose manifest. It has its own files that replace ansible templates and inventory.

### Directories layout

```
|- docker-compose   - manifests for docker-compose, it must be run from this directory
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
|- Vagrantfile      - manifest for vagrant machine
```

### Checkout and run scripts

All command are started from the root directory of the repo:

```
git clone git@github.com:freddygood/guacamole3.git
cd guacamole3
```

### Prepare virtual server

```
make up
make prepare
```

Those tasks perform the following:

- spin up vagrant machine
- forward port 8181
- install python and pip
- install docker

### Create and run jenkins containers

```
make create
```

This task performs the following:

- creates 2 docker images inside a vargant machine
- create docker volumes
- create docker network
- spin up docker containers
- display connecting information

These tasks are started after the master starts:

- update admin password
- install required plugins
- create SSH credentials for slaves
- register and launch slaves

### Delete all created resource

```
make clean
```

This task stop and destroy containers, images, network and volumes.

### Prune all unused docker resources

```
make prune
```

### Collect everything together

Create machine and spin up containers

```
git clone git@github.com:freddygood/guacamole3.git
cd guacamole3

make up prepare create
```

Access jenkins via http://127.0.0.1:8181 with credentials provided

When everything is finish

```
make prune clean destroy
```

### Bonus

All docker-compose related files are in a separate directory because compose doesn't support templating and inventory.
Docker must be installed and run locally.
To build images and spin up containers with compose run the command:

```
cd docker-compose
docker-compose up
```

### good to be done

- pull ssh keys from images
- add configurable parameters - such as slave number, java_opt, etc
- split create task to shorter tasks - such as create_images, run master, run slave, etc
- rewrite jenkins startup scripts and make them more convenient
- catch containers logs with rsyslog
- use external docker registery for posible sclability
