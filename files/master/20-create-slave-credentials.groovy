#!groovy
import jenkins.model.*
import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.common.*
import com.cloudbees.plugins.credentials.domains.*
import com.cloudbees.plugins.credentials.impl.*
import com.cloudbees.jenkins.plugins.sshcredentials.impl.*
import hudson.plugins.sshslaves.*

domain = Domain.global()
store = Jenkins.instance.getExtensionList('com.cloudbees.plugins.credentials.SystemCredentialsProvider')[0].getStore()

String keyfile = "{{ jenkins_home }}/.ssh/slave_rsa"

privateKey = new BasicSSHUserPrivateKey(
	CredentialsScope.GLOBAL,
	"{{ jenkins_agent_credentials }}",
	"{{ jenkins_user }}",
	new BasicSSHUserPrivateKey.FileOnMasterPrivateKeySource(keyfile),
	"",
	""
)

store.addCredentials(domain, privateKey)
