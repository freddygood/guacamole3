#!groovy
import hudson.model.*
import jenkins.model.*
import hudson.slaves.*
import hudson.slaves.EnvironmentVariablesNodeProperty.Entry
import hudson.plugins.sshslaves.SSHLauncher
import hudson.plugins.sshslaves.verifiers.*

def agents = [ 'slave1', 'slave2' ] as String[]

Jenkins.instance.nodes.each {
    println "BEFORE - Agent: $it"
}

// Loop through nodes array
agents.each {
    addNode(it)
}

Jenkins.instance.nodes.each {
    println "AFTER - Agent: $it"
}

def addNode(String name) {
    SshHostKeyVerificationStrategy hostKeyVerificationStrategy = new NonVerifyingKeyVerificationStrategy()

    ComputerLauncher launcher = new SSHLauncher(
        name,
        22,
        "{{ jenkins_agent_credentials }}",
        (String)null,
        (String)null,
        (String)null,
        (String)null,
        (Integer)null,
        (Integer)null, 
        (Integer)null,
        hostKeyVerificationStrategy
    )

    DumbSlave agent = new DumbSlave(
        name,
        "{{ jenkins_agent_home }}",
        launcher
    )

    agent.nodeDescription = "Auto-created agent node $name"
    agent.numExecutors = {{ jenkins_agent_executors }}
    agent.labelString = "label-$name"
    agent.mode = Node.Mode.NORMAL
    agent.retentionStrategy = new RetentionStrategy.Always()

    Jenkins.instance.addNode(agent)
    println "--> agent '$name' has been created"
}
