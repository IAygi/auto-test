package ru.iaygi.common;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Locale;
import java.util.Map;

import static ru.iaygi.common.CommonTextValues.*;

public class FakeData {

    private static final Faker faker = new Faker();
    private static final Faker fakerLocalRu = new Faker(new Locale("ru-RU"));

    public static String login() {
        return fakerResult(faker.name().username());
    }

    public static String firstNameRu() {
        return fakerResult(fakerLocalRu.name().firstName());
    }

    public static String firstName() {
        return fakerResult(faker.name().firstName());
    }

    public static String lastNameRu() {
        return fakerResult(fakerLocalRu.name().lastName());
    }

    public static String lastName() {
        return fakerResult(faker.name().lastName());
    }

    public static String withMiddleNane() {
        return fakerResult(fakerLocalRu.name().nameWithMiddle());
    }

    public static String fullName() {
        return fakerResult(fakerLocalRu.name().fullName());
    }

    public static String companyName() {
        return fakerResult(fakerLocalRu.company().name());
    }

    public static String companyFullName() {
        return fakerResult(faker.company().suffix() + " " +
                fakerResult(faker.company().name()) + " " +
                fakerResult(faker.company().catchPhrase()));
    }

    public static String currencyCode() {
        return faker.currency().code();
    }

    public static String currencyName() {
        return faker.currency().name();
    }

    public static String longCompanyName() {
        return fakerResult(RandomStringUtils.random(6, RU_SELECTIVE_CHARS).toUpperCase() + " "
                + fakerLocalRu.company().name()
                + " " + fakerLocalRu.company().suffix()
                + " " + fakerLocalRu.company().suffix());
    }

    public static String countryName() {
        return fakerResult(faker.address().country());
    }

    public static String countryNameRu() {
        return fakerResult(fakerLocalRu.address().country());
    }

    public static String countryRegion() {
        return fakerResult(faker.address().state());
    }

    public static String countryRegionRu() {
        return fakerResult(fakerLocalRu.address().state());
    }

    public static String cityNameRu() {
        return fakerResult(fakerLocalRu.address().city());
    }

    public static String cityName() {
        return fakerResult(faker.address().city());
    }

    public static String email() {
        return faker.internet().emailAddress();
    }

    public static String website() {
        return faker.internet().domainName();
    }

    public static String phoneNumber() {
        return fakerLocalRu.phoneNumber().phoneNumber().replaceAll("[()-]", "");
    }

    public static String phoneNumberWithoutPlus() {
        return fakerLocalRu.phoneNumber().phoneNumber().replaceAll("[+()-]", "");
    }

    public static int adultAge() {
        return faker.number().numberBetween(18, 60);
    }

    public static String numbersCollection(int length) {
        return faker.number().digits(length);
    }

    public static int anyNumber(int arg1, int arg2) {
        return faker.number().numberBetween(arg1, arg2);
    }

    public static int number() {
        return faker.number().numberBetween(1, 100);
    }

    public static int bigNumber() {
        return faker.number().numberBetween(1000, 100000);
    }

    public static int veryBigNumber() {
        return faker.number().numberBetween(Integer.MAX_VALUE - 10000, Integer.MAX_VALUE);
    }

    public static String jobType() {
        return faker.job().seniority() + " " + faker.job().position() + " "
                + faker.job().title();
    }

    public static String jobTitle() {
        return faker.job().title();
    }

    public static String symbolsCollection(int length) {
        return faker.lorem().characters(length);
    }

    public static String lordOfTheRings() {
        return faker.lordOfTheRings().character() + " from "
                + faker.lordOfTheRings().location();
    }

    public static String harryPotterLocation() {
        return faker.harryPotter().location();
    }

    public static String harryPotterSpell() {
        return faker.harryPotter().spell();
    }

    public static String harryPotterBook() {
        return faker.harryPotter().book();
    }

    public static String harryPotterHouse() {
        return faker.harryPotter().house();
    }

    public static String harryPotterCharacter() {
        return faker.harryPotter().character();
    }

    public static String fullAddress() {
        return fakerLocalRu.address().fullAddress().substring(7);
    }

    public static String charsCollection(int count) {
        String word = "";
        for (int i = 0; i < count; i++) {
            word += faker.letterify("?");
        }
        return word;
    }

    public static String charsCollectionRu(int count) {
        String word = "";
        for (int i = 0; i < count; i++) {
            word += fakerLocalRu.letterify("?");
        }
        return word;
    }

    private static String fakerResult(String result) {
        return result.replaceAll("[.^=\"':,&$`~]", "-");
    }

    public static String purpose() {
        return fakerLocalRu.company().name();
    }

    public Map<String, String> fullGenderName() {
        String firstName = firstName();
        String firstNameLastChar = firstName.substring(firstName.length() - 1);
        String lastName = lastName();
        String lastNameLastChar = lastName.substring(lastName.length() - 1);
        String middleName = "";

        if (firstNameLastChar.equals("а") || firstNameLastChar.equals("я") || firstNameLastChar.equals(
                "ь")) {
            while (!lastNameLastChar.equals("а")) {
                lastName = lastName();
                lastNameLastChar = lastName.substring(lastName.length() - 1);
            }
            middleName = "Ивановна";
        } else {
            while (lastNameLastChar.equals("а")) {
                lastName = lastName();
                lastNameLastChar = lastName.substring(lastName.length() - 1);
            }
            middleName = "Иванович";
        }

        return Map.of(
                "firstName", firstName,
                "lastName", lastName,
                "middleName", middleName
        );
    }

    public String commonName() {
        String commonNane = "";
        for (int i = 0; i < 3; i++) {
            String firstLetter = RandomStringUtils.random(1, RU_CONSONANT_CHARS).toUpperCase();
            String secondLetter = RandomStringUtils.random(1, RU_VOWEL_LETTERS);
            commonNane += firstLetter + secondLetter;
            if (i != 2) {
                commonNane += "-";
            }
        }
        return commonNane;
    }
}
