Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/xenial64"
  config.vm.hostname = "jenkins-host"
  config.vm.define "jenkins-box"
  config.vm.network "forwarded_port", guest: 8181, host: 8181
end
