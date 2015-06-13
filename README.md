cli
===

Sphinx generates shell tests out of partially roughed out Java classes.

A quick and dirty way to install Sphinx:

1. Run `mvn package`
2. Run `mvn dependency:copy-dependencies`
3. Copy `target/dependency` to a permanent `lib` folder, say `c:/Users/john_doe/lib`
4. Also copy `target/lospi-core-X.X-SNAPSHOT.jar` into this lib directory.

You can run the tool by executing
	
	java -cp "c:/Users/john_doe/lib/*" net.lospi.Sphinx
	
Of course, you can alias this to make life easier:

	alias sphx "java -cp \"c:/Users/john_doe/lib/*\" net.lospi.Sphinx"