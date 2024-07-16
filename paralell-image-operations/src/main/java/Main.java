import org.apache.commons.lang3.tuple.Pair;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        String source_path = "/Users/macieklazar/IdeaProjects/pt_lab6/src/main/img/source";
        String result_path = "/Users/macieklazar/IdeaProjects/pt_lab6/src/main/img/result";

        List<Path> files = null;
        Path source = Path.of(source_path);
        try (Stream<Path> stream = Files.list(source)) {
            files = stream.collect(Collectors.toList());
        } catch (IOException ex) {

        }


        Stream<Pair<String, BufferedImage>> pairs = files.stream()
                .map(value -> mapVal(value));


        for (int i = 1; i<=9; i++) {
            long time = System.currentTimeMillis();
            ForkJoinPool pool = new ForkJoinPool(i);
            try {
                List<Path> finalFiles = files;
                pool.submit(() -> {
                    finalFiles.stream()
                            .parallel()
                            .map(value -> mapVal(value))
                            .filter(value -> value.getRight() != null)
                            .map(value -> ChangePixels(value))
                            .forEach(value -> saveImg(value));
                }).get();
            } catch (InterruptedException | ExecutionException ex) {

            }

            System.out.print("Ilosc watkow" + i + " czas: ");
            System.out.println(System.currentTimeMillis() - time);
        }
    }

    public static Pair<String, BufferedImage> mapVal(Path p) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(p.toFile());
        } catch (IOException ex) {

        }
        return Pair.of(p.toString(), img);
    }

    public static Pair<String, BufferedImage> ChangePixels(Pair<String, BufferedImage> pair) {
        BufferedImage original = pair.getRight();
        BufferedImage result = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());

        for (int i = 0; i < original.getWidth(); i++) {
            for (int j = 0; j < original.getHeight(); j++) {
                int rgb = original.getRGB(i, j);
                Color color = new Color(rgb);
                int gray = (int) Math.round((color.getRed()+color.getGreen()+color.getBlue())/3.0);
                Color newColor = new Color(gray, gray, gray);
                int newRgb = newColor.getRGB();
                result.setRGB(i, j, newRgb);
            }
        }

        return Pair.of(pair.getLeft(), result);
    }

    public static void saveImg(Pair<String, BufferedImage> pair) {
        String[] splits = pair.getLeft().split("/");
        String file = splits[splits.length-1];
        String format = file.split("\\.")[1];
        try {
            ImageIO.write(pair.getRight(), format, new File("/Users/macieklazar/IdeaProjects/pt_lab6/src/main/img/result/"+file));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
