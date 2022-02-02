package com.namkyujin.game.archunit;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class DependencyRuleTests {

    @Test
    void domainLayerDoesNotDependOnApplicationLayer() {
        noClasses()
                .that()
                .resideInAPackage("com.namkyujin.game.domain..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("com.namkyujin.game.application..")
                .check(new ClassFileImporter()
                        .importPackages("com.namkyujin.game.."));
    }

}
