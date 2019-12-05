#PROJECT_VERSION=`mvn -q -Dexec.executable="echo" -Dexec.args='${project.version}' --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec` && \
#read -p "Release version ($PROJECT_VERSION): " CURRENT_VERSION && \
#mvn -q versions:set -DnewVersion=$CURRENT_VERSION && \
#mvn -q clean deploy && \
#mvn deploy -Dregistry=https://maven.pkg.github.com/MBoegers -Dtoken=074009c3f8cde9634e01f3e354fc9427e1783bc2 && \
#mvn -q versions:commit && \
#git add --all && \
#git commit -m "Created release version $CURRENT_VERSION" && \
#git tag $CURRENT_VERSION && \
#git push --tags && \
#git push --tags github && \
git checkout -B dev && \
read -p "Next version (SNAPSHOT will be added automatically): " NEXT_VERSION && \
mvn -q versions:set -DnewVersion="$NEXT_VERSION-SNAPSHOT" && \
mvn -q versions:commit && \
mvn -q clean verify && \
git add --all && \
git commit -m "Starting to work on $NEXT_VERSION-SNAPSHOT" && \
git push --all #&& \
#git push --all github
