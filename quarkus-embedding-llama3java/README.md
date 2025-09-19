# Quarkus demo: embedding Llama3-java

This particular demo is minimalistic, to show what's required to integrate [Llama3-java](https://github.com/mukel/llama3.java) with Quarkus.

The Llama3-java library is a Java implementation of the Llama model, which is a large language model (LLM) designed for efficient inference.
It is designed to run on regular CPUs leveraging their vector engines for optimal performance and can easily be embedded in Java applications.

Quarkus provides an `quarkus-langchain4j-llama3-java` extension, which allows you to integrate it into your Quarkus applications through its support for langchain4j: just adding the dependency takes care of the integration.

As with any Quarkus extension, it also supports compilation to a native executable through GraalVM's `native-image`.

## Requirements

For building and running in a regular JVM, you'll need Java 21 or newer.

To try it out as a native-image, you'll either need to have GraalVM installed locally, or a container engine such as Docker or Podman; having such a container engine will allow the Quarkus builds to fetch and run an appropriate GraalVM distribution automatically, if no other GraalVM installation can be found.

## Build the demo

Nothing special here.

```shell script
./mvnw package
```

## Running the demo in a JVM

The Llama3-java implementation requires the experimental vector API; this is available in the JDK but needs to be explicitly allowed.
To enable this, run the application with the following arguments:

```shell script
java --enable-preview --add-modules jdk.incubator.vector -jar target/quarkus-app/quarkus-run.jar
```

This example is a simple Command-Line-Interface (CLI) application, with no HTTP endpoints: you'll see the generated output in the console and then it will quit.
It's not widely known, but Quarkus has excellent support to build CLI applications.

The example code also accepts two optional arguments, as in the following example:

```shell script
java --enable-preview --add-modules jdk.incubator.vector -jar target/quarkus-app/quarkus-run.jar "GraalVM and Quarkus in Zurich" --lines 5
```

## Building the demo as a native image

Such short lived CLI tools represent an excellent use case for native images.

To build one, again nothing unusual is required: we'll build again, this time activating the `native` profile.

```shell script
./mvnw package -Dnative
```

Also fairly standard. Tested with GraalVM EE 25: you'll need a modern version!

## Running the demo as a native image

```shell script
./target/quarkus-embedding-llama3java-1.0.0-SNAPSHOT-runner
```

Some interesting statistics will be logged; it should confirm that when running as a native image, loading the tensors from the model is extremely fast: it has been pre-processed during the build, embedding the necessary data in the native image.

And of course it accepts the same arguments as the JVM version.

```shell script
./target/quarkus-embedding-llama3java-1.0.0-SNAPSHOT-runner "Quarkus and GraalVM in Zurich" --lines 5
```

## Related Links

- Llama3-java: https://github.com/mukel/llama3.java
- Quarkus: https://quarkus.io/
- GraalVM: https://www.graalvm.org/
- LangChain4j: https://docs.langchain4j.dev/
- LangChain4j Llama3 Java ([guide](https://docs.quarkiverse.io/quarkus-langchain4j/dev/index.html)): Provides integration of Quarkus LangChain4j with Llama3 Java

