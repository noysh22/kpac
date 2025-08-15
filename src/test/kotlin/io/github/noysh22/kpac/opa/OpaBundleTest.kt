package io.github.noysh22.kpac.opa

import io.github.noysh22.kpac.opa.OpaBundle
import io.kotest.assertions.asClue
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileInputStream

class OpaBundleTest {

    companion object {
        private const val BUNDLE_PATH = "src/rego/example_policy_bundle.tar.gz"
        private val bundleFile = File(BUNDLE_PATH)
    }

    @Test
    fun `test fromTarGz loads bundle successfully`() {
        val runfilesBundleFile = File(System.getProperty("user.dir") + "/" + BUNDLE_PATH)
        val actualBundleFile = if (bundleFile.exists()) bundleFile else runfilesBundleFile

        "Bundle file should exist".asClue {
            actualBundleFile.exists() shouldBe true
        }

        val bundle = OpaBundle.fromTarGz(actualBundleFile.absolutePath)

        "Bundle should be loaded successfully".asClue {
            bundle.shouldNotBeNull()
        }
        "Bundle should have policy".asClue {
            bundle.policy.shouldNotBeNull()
        }
        "Bundle should have data".asClue {
            bundle.data.shouldNotBeNull()
        }
        "Data should not be empty".asClue {
            bundle.data.isNotEmpty() shouldBe true
        }
    }

    @Test
    fun `test fromInputStream loads bundle successfully`() {
        val runfilesBundleFile = File(System.getProperty("user.dir") + "/" + BUNDLE_PATH)
        val actualBundleFile = if (bundleFile.exists()) bundleFile else runfilesBundleFile

        "Bundle file should exist".asClue {
            actualBundleFile.exists() shouldBe true
        }

        val bundle = FileInputStream(actualBundleFile).use { inputStream ->
            OpaBundle.fromInputStream(inputStream)
        }

        "Bundle should be loaded successfully".asClue {
            bundle.shouldNotBeNull()
        }
        "Bundle should have policy".asClue {
            bundle.policy.shouldNotBeNull()
        }
        "Bundle should have data".asClue {
            bundle.data.shouldNotBeNull()
        }
        "Data should not be empty".asClue {
            bundle.data.isNotEmpty() shouldBe true
        }
    }

    @Test
    fun `test bundle data is valid JSON`() {
        val bundleFile = File(BUNDLE_PATH)
        val runfilesBundleFile = File(System.getProperty("user.dir") + "/" + BUNDLE_PATH)
        val actualBundleFile = if (bundleFile.exists()) bundleFile else runfilesBundleFile

        val bundle = OpaBundle.fromTarGz(actualBundleFile.absolutePath)

        "Bundle should be loaded".asClue {
            bundle.shouldNotBeNull()
        }
        "Data should be valid JSON format".asClue {
            bundle.data shouldContain "{"
        }
    }

    @Test
    fun `test both loading methods produce same result`() {
        val bundleFile = File(BUNDLE_PATH)
        val runfilesBundleFile = File(System.getProperty("user.dir") + "/" + BUNDLE_PATH)
        val actualBundleFile = if (bundleFile.exists()) bundleFile else runfilesBundleFile

        val bundleFromTarGz = OpaBundle.fromTarGz(actualBundleFile.absolutePath)
        val bundleFromInputStream = FileInputStream(actualBundleFile).use { inputStream ->
            OpaBundle.fromInputStream(inputStream)
        }

        "Both bundles should be loaded".asClue {
            bundleFromTarGz.shouldNotBeNull()
            bundleFromInputStream.shouldNotBeNull()
        }
        "Both bundles should have same data".asClue {
            bundleFromTarGz.data shouldBe bundleFromInputStream.data
        }
    }
}
