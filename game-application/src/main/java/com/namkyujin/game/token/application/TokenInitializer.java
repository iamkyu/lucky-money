package com.namkyujin.game.token.application;

import com.namkyujin.game.core.domain.token.Token;
import com.namkyujin.game.core.domain.token.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;


// local only component
@RequiredArgsConstructor
public class TokenInitializer implements CommandLineRunner {

    private final TokenRepository tokenRepository;

    @Override
    public void run(String... args) {
        List<String> tokens = tokens();
        Collections.shuffle(tokens);
        tokenRepository.saveAll(tokens.stream()
                .map(Token::new)
                .collect(toList()));
    }

    private List<String> tokens() {
        return Arrays.asList(
                "AAA",
                "AAB",
                "AAC",
                "AAD",
                "AAE",
                "AAF",
                "AAG",
                "AAH",
                "AAI",
                "AAJ",
                "AAK",
                "AAL",
                "AAM",
                "AAN",
                "AAO",
                "AAP",
                "AAQ",
                "AAR",
                "AAS",
                "AAT",
                "AAU",
                "AAV",
                "AAW",
                "AAX",
                "AAY",
                "AAZ",
                "AA0",
                "AA1",
                "AA2",
                "AA3",
                "AA4",
                "AA5",
                "AA6",
                "AA7",
                "AA8",
                "AA9",
                "AAa",
                "AAb",
                "AAc",
                "AAd",
                "AAe",
                "AAf",
                "AAg",
                "AAh",
                "AAi",
                "AAj",
                "AAk",
                "AAl",
                "AAm",
                "AAn",
                "AAo",
                "AAp",
                "AAq",
                "AAr",
                "AAs",
                "AAt",
                "AAu",
                "AAv",
                "AAx",
                "AAy",
                "AAz",
                "ABA",
                "ABB",
                "ABC",
                "ABD",
                "ABE",
                "ABF",
                "ABG",
                "ABH",
                "ABI",
                "ABJ",
                "ABK",
                "ABL",
                "ABM",
                "ABN",
                "ABO",
                "ABP",
                "ABQ",
                "ABR",
                "ABS",
                "ABT",
                "ABU",
                "ABV",
                "ABW",
                "ABX",
                "ABY",
                "ABZ",
                "AB0",
                "AB1",
                "AB2",
                "AB3",
                "AB4",
                "AB5",
                "AB6",
                "AB7",
                "AB8",
                "AB9",
                "ABa",
                "ABb",
                "ABc"
        );
    }
}