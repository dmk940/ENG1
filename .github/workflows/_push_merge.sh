EXIT=1
while [ $EXIT != 0 ]
do
  git pull
  git push
  EXIT=$?
done

# This script is necessary because of the multiple workflow tasks that push to the repo (e.g. Javadocs)
# When they run in parallel, they will modify other (non-conflicting files) causing this push to fail.
# Because they're guaranteed to be non-conflicting, this solution is acceptable.
