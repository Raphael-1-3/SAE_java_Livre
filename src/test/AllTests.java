package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    testAdmin.class,
    testAuteur.class,
    testBD.class,
    testClassification.class,
    testClient.class,
    testCommande.class,
    testEditeur.class,
    testLivre.class,
    testMagasin.class,
    testVendeur.class
})
public class AllTests {}
