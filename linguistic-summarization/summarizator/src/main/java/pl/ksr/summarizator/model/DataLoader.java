package pl.ksr.summarizator.model;

import pl.ksr.summarizator.model.fuzzylogic.DoubleSubjectTerm;
import pl.ksr.summarizator.model.fuzzylogic.SingleSubjectTerm;
import pl.ksr.summarizator.view.DstViewModel;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class DataLoader {
    private static final String carsPath = "data/selected_cars.csv";
    private static final String savePath = "output.csv";

    public static List<CarObject> loadCars() {
        boolean header = true;
        List<String> headers = new ArrayList<>();
        List<CarObject> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(carsPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (header) {
                    headers.addAll(Arrays.asList(values));
                    header = false;
                    continue;
                }
                CarObject car = new CarObject(headers, Arrays.asList(values));
                records.add(car);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return records;
    }

    public static List<CarObject> getSubjectObjects(List<CarObject> cars, String subject, String subjectType) {
        return cars.stream()
                .filter(car -> car.getCarProperties().get(subject).equals(subjectType))
                .toList();
    }

    public static void saveResults(List<SingleSubjectTerm> results) {
        try (Writer fw = new BufferedWriter(
                new OutputStreamWriter(Files.newOutputStream(Paths.get(savePath)), StandardCharsets.UTF_8))) {
            fw.write("\uFEFF");
            fw.write("Term, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, Optimal Summary\n");
            for (SingleSubjectTerm term : results) {
                fw.write(term.getTerm() + ", " +
                        format(term.getT1()) + ", " +
                        format(term.getT2()) + ", " +
                        format(term.getT3()) + ", " +
                        format(term.getT4()) + ", " +
                        format(term.getT5()) + ", " +
                        format(term.getT6()) + ", " +
                        format(term.getT7()) + ", " +
                        format(term.getT8()) + ", " +
                        format(term.getT9()) + ", " +
                        format(term.getT10()) + ", " +
                        format(term.getT11()) + ", " +
                        format(term.getOptimalSummary()) + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveDoubleSubjectResults(List<DoubleSubjectTerm> terms) {
        try (Writer fw = new BufferedWriter(
                new OutputStreamWriter(Files.newOutputStream(Paths.get(savePath)), StandardCharsets.UTF_8))) {
            fw.write("\uFEFF");
            fw.write("Form, Term, T1\n");
            for (DoubleSubjectTerm term : terms) {
                fw.write("1" + ", " + term.getTerm1() + ", " + format(term.getDot1()) + "\n"
                        + "2" + ", " + term.getTerm2() + ", " + format(term.getDot2()) + "\n"
                        + "3" + ", " + term.getTerm3() + ", " + format(term.getDot3()) + "\n"
                        + "4" + ", " + term.getTerm4() + ", " + format(term.getDot4()) + "\n"
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveDoubleSubjectResults2(List<DstViewModel> terms) {
        try (Writer fw = new BufferedWriter(
                new OutputStreamWriter(Files.newOutputStream(Paths.get(savePath)), StandardCharsets.UTF_8))) {
            fw.write("\uFEFF");
            fw.write("Form, Term, T1\n");
            for (DstViewModel term : terms) {
                fw.write(term.getForm() + ", " + term.getTerm() + ", " + format(term.getT1()) + "\n"
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String format(double value) {
        return String.format(Locale.US, "%.2f", value);
    }
}

