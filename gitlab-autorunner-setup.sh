#!/bin/bash
echo 'starting setup...'
echo 'installing docker...'
curl -sSL https://get.docker.com/ | sh
echo 'installing gitlab-ci-multi-runner repository'
curl -L https://packages.gitlab.com/install/repositories/runner/gitlab-ci-multi-runner/script.deb.sh | sudo bash
echo 'installing gitlab-ci-multi-runner'
sudo apt-get install gitlab-ci-multi-runner
echo 'registering gitlab-ci-multi-runner'
gitlab-ci-multi-runner register --non-interactive --name "$HOSTNAME" --url "https://gitlab.com/ci" \
--registration-token "" \
--executor "docker" \
--docker-image 'docker:git' \
--docker-volumes /root/.m2:/root/.m2 \
--docker-volumes /var/run/docker.sock:/var/run/docker.sock \
--docker-privileged true
echo 'setup complete!'