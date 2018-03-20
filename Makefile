
export ANSIBLE_DEPRECATION_WARNINGS=True
export ANSIBLE_HOST_KEY_CHECKING=False

up:
	vagrant up

destroy:
	vagrant destroy -f

prepare:
	ansible-galaxy install -p roles -r requirements.yml
	ansible-playbook -i inventory/vagrant.py tasks/prepare.yml

create:
	ansible-playbook -i inventory/vagrant.py tasks/create.yml

clean:
	ansible-playbook -i inventory/vagrant.py tasks/delete.yml

prune:
	ansible-playbook -i inventory/vagrant.py tasks/prune.yml

all: prepare create
