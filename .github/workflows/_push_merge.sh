EXIT=1
while [ $EXIT != 0 ]
do
  git pull
  git push
  EXIT=$?
done
