package com.pece.agencia.architecture;

import com.pece.agencia.api.ApiApplication;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

import static java.lang.String.format;

class ArchitectureTest {

    @Nested
    @DisplayName("ACL upstream tests")
    class AclUpstreamRulesTest {
        private void checkAclUpstreamRule(String acl) {
            JavaClasses importedClasses = importMainClasses();
            ArchRuleDefinition.noClasses()
                    .that()
                    .resideOutsideOfPackages(
                            format("com.pece.agencia.api.core.service.acl.%s..", acl),
                            format("com.pece.agencia.api.%s..", acl)
                    )
                    .should()
                    .dependOnClassesThat()
                    .resideInAnyPackage(format("com.pece.agencia.api.%s..", acl))
                    .because(format("Apenas classes do pacote acl.%s podem acessar o context %s", acl, acl))
                    .check(importedClasses);

        }

        @Test
        @DisplayName("Somente classes da ACL podem acessar o contexto delimitado locacao veiculo")
        void onlyAclVeiculoServiceCanAccessVeiculoPackage() {
            checkAclUpstreamRule("veiculo");
        }

        @Test
        @DisplayName("Somente classes da ACL podem acessar o contexto delimitado hoteleira")
        void onlyAclHotelariaServiceCanAccessHotelariaPackage() {
            checkAclUpstreamRule("hotelaria");
        }

        @Test
        @DisplayName("Somente classes da ACL podem acessar o contexto delimitado translado aereo")
        void onlyAclTransladoAereoServiceCanAccessAereoPackage() {
            checkAclUpstreamRule("aereo");
        }


        @Test
        @DisplayName("Somente classes da ACL podem acessar o contexto delimitado de pagamento")
        void onlyAclPagamentoServiceCanAccessPagamentoPackage() {
            checkAclUpstreamRule("pagamento");
        }
    }


    @DisplayName("Garanta que não há violações de arquitetura modular (acesso via APIs)")
    @Test
    void ensureModuleAccessThruAPIs() {
        ApplicationModules.of(ApiApplication.class)
                .detectViolations()
                .throwIfPresent();
    }

    private JavaClasses importMainClasses() {
        // Importa apenas classes do diretório de produção (target/classes)
        return new ClassFileImporter().importPath("target/classes");
    }


    @Test
    void servicesShouldResideInServicePackage() {
        JavaClasses importedClasses = importMainClasses();
        ArchRuleDefinition.classes()
                .that().haveSimpleNameEndingWith("Service")
                .should().resideInAPackage("..service..")
                .check(importedClasses);
    }

    @Test
    void controllersShouldOnlyDependOnServiceAndDomain() {
        JavaClasses importedClasses = importMainClasses();
        ArchRuleDefinition.noClasses()
                .that().resideInAPackage("..controller..")
                .should().dependOnClassesThat().resideInAnyPackage("..repository..")
                .because("Controllers não devem depender de repositórios diretamente, apenas de services e domain").check(importedClasses);
    }

    @Test
    void servicesShouldOnlyDependOnRepositoryAndDomain() {
        JavaClasses importedClasses = importMainClasses();
        ArchRuleDefinition.noClasses()
                .that().resideInAPackage("..service..")
                .should().dependOnClassesThat().resideInAnyPackage("..controller..")
                .because("Services não devem depender de controllers, apenas de repository e domain").check(importedClasses);
    }

    @Test
    void repositoriesShouldOnlyDependOnDomain() {
        JavaClasses importedClasses = importMainClasses();
        ArchRuleDefinition.noClasses()
                .that().resideInAPackage("..repository..")
                .should().dependOnClassesThat().resideInAnyPackage("..controller..", "..service..")
                .because("Repositories não devem depender de controllers ou services, apenas de domain").check(importedClasses);
    }

    @Test
    void domainShouldNotDependOnOtherLayers() {
        JavaClasses importedClasses = importMainClasses();
        ArchRuleDefinition.noClasses()
                .that().resideInAPackage("..domain..")
                .should().dependOnClassesThat().resideInAnyPackage("..controller..", "..service..", "..repository..")
                .because("Domain não deve depender de nenhuma camada externa").check(importedClasses);
    }
}
