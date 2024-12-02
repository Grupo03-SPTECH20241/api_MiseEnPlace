#!/bin/bash
set -x

# tentando parar o processo (se ocorrer erro, ignora e segue)
kill -9 `cat api_pid.txt`

set -e
# daqui em diante, se ocorrer erro, interrompe o script

# tentando iniciar a API (se ocorrer erro, interrompe o script)
sh /home/ubuntu/deploy-api/api-start.sh