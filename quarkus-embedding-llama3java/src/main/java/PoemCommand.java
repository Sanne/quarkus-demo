import jakarta.inject.Inject;
import picocli.CommandLine;

@CommandLine.Command(name = "poem", mixinStandardHelpOptions = true)
public class PoemCommand implements Runnable {

    @CommandLine.Parameters(paramLabel = "<topic>", defaultValue = "Quarkus and GraalVM",
            description = "The topic.")
    String topic;

    @CommandLine.Option(names = "--lines", defaultValue = "4",
            description = "The number of lines in the poem.")
    int lines;

    @Inject
    MyAiService myAiService;

    @Override
    public void run() {
        System.out.println(myAiService.writeAPoem(topic, lines));
    }
}