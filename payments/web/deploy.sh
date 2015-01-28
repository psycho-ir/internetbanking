#!/bin/bash
warfile=/home/jalal/projects/samen/$1/web/target/$1-war.war
if [ -e "$warfile" ]
then
   echo "deploying $warfile ..."
else
   echo "$warfile does not exist try another path..."
   warfile=/home/jalal/projects/samen/$1/war/target/$1-war.war
   echo "deploying $warfile ..."
fi
if [ -e "$warfile" ]
then
   cp $warfile $JBOSS_HOME/standalone/deployments
   touch $JBOSS_HOME/standalone/deployments/$1-war.war.dodeploy
   echo "Deployed successfully"
else
   echo "Can not find war file to deploy. maybe the path is not correct or war project is not built"
fi
