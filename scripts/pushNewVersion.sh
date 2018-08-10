#!/bin/sh
CURRENTVERSION=$(awk '$1=="versionCode" {print $2}' version.properties)
git config --global user.name "Tamás Varga"
git config --global user.email mail@tvarga.hu
git branch --set-upstream-to=origin/$CIRCLE_BRANCH $CIRCLE_BRANCH
git add -u
git commit -m "pushing version ${CURRENTVERSION} [skip ci]"
git pull --rebase
git push origin $CIRCLE_BRANCH
