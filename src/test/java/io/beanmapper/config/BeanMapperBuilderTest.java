package io.beanmapper.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import io.beanmapper.BeanMapper;
import io.beanmapper.exceptions.BeanConfigurationOperationNotAllowedException;
import mockit.Deencapsulation;

import org.junit.Test;

public class BeanMapperBuilderTest {

    @Test(expected = BeanConfigurationOperationNotAllowedException.class)
    public void setTargetClassOnCoreThrowsException() {
        BeanMapperBuilder builder = new BeanMapperBuilder();
        builder.setTargetClass(null);
    }

    @Test(expected = BeanConfigurationOperationNotAllowedException.class)
    public void setTargetOnCoreThrowsException() {
        BeanMapperBuilder builder = new BeanMapperBuilder();
        builder.setTarget(null);
    }

    @Test(expected = BeanConfigurationOperationNotAllowedException.class)
    public void setDownsizeSourceOnCoreThrowsException() {
        BeanMapperBuilder builder = new BeanMapperBuilder();
        builder.downsizeSource(null);
    }

    @Test(expected = BeanConfigurationOperationNotAllowedException.class)
    public void setDownsizeTargetOnCoreThrowsException() {
        BeanMapperBuilder builder = new BeanMapperBuilder();
        builder.downsizeTarget(null);
    }

    @Test(expected = BeanConfigurationOperationNotAllowedException.class)
    public void setCollectionClassOnCoreThrowsException() {
        BeanMapperBuilder builder = new BeanMapperBuilder();
        builder.setCollectionClass(null);
    }

    @Test
    public void isConverterChoosableForCoreConfig() {
        BeanMapper beanMapper = new BeanMapperBuilder().build();
        assertFalse(
                "The Core configuration assumes that a top-level call always assumes a pure mapping (ie, not using converters)",
                beanMapper.getConfiguration().isConverterChoosable());
    }

    @Test
    public void isConverterChoosableSettableForCoreConfig() {
        BeanMapper beanMapper = new BeanMapperBuilder()
                .setConverterChoosable(true)
                .build();
        assertTrue(
                "The Core configuration converterChoosable is settable)",
                beanMapper.getConfiguration().isConverterChoosable());
    }

    @Test
    public void isConverterChoosableCustomForOverrideConfig() {
        BeanMapper beanMapper = new BeanMapperBuilder().build(); // Core wrap
        beanMapper = beanMapper
                .wrap()
                .setConverterChoosable(false) // <<< override the converter choosable option here
                .build(); // Override wrap
        assertFalse(
                "The Override configuration has a custom override for the converter choosable option, which should be false",
                beanMapper.getConfiguration().isConverterChoosable());
    }

    @Test
    public void currentConfigOnCoreLeadsToOverrideConfig() {
        BeanMapper beanMapper = new BeanMapperBuilder().build(); // Core wrap
        beanMapper = beanMapper.wrap().build(); // Wrap in an override wrap
        Object parentConfiguration = Deencapsulation.getField(beanMapper.getConfiguration(), "parentConfiguration");
        assertNotNull(parentConfiguration);
    }

    @Test
    public void newConfigOnOverrideLeadsToExistingConfig() {
        BeanMapper beanMapper = new BeanMapperBuilder().build(); // Core wrap
        beanMapper = beanMapper.wrap().build(); // Wrap in an override wrap
        Configuration currentConfiguration = beanMapper.getConfiguration();
        beanMapper = beanMapper.wrap().build(); // <<< explicitly wrap in an override wrap
        assertNotSame("The configuration must be a new one from the one we already had",
                currentConfiguration, beanMapper.getConfiguration());
    }

    @Test
    public void strictMappingConventionForCoreConfig() {
        BeanMapper beanMapper = new BeanMapperBuilder()
                .setStrictSourceSuffix("Frm")
                .setStrictTargetSuffix("Rslt")
                .build(); // Core wrap
        Configuration currentConfiguration = beanMapper.getConfiguration();
        assertEquals("Frm", currentConfiguration.getStrictSourceSuffix());
        assertEquals("Rslt", currentConfiguration.getStrictTargetSuffix());
    }

    @Test
    public void strictMappingConventionForOverrideConfig() {
        BeanMapper beanMapper = new BeanMapperBuilder().build(); // Core wrap
        beanMapper = beanMapper.wrap()
                .setStrictSourceSuffix("Frm")
                .build(); // Wrap in an override wrap

        Configuration currentConfiguration = beanMapper.getConfiguration();
        assertEquals("Frm",
                currentConfiguration.getStrictMappingProperties().getStrictSourceSuffix());
        assertEquals("Result",
                currentConfiguration.getStrictMappingProperties().getStrictTargetSuffix());
        assertEquals(true,
                currentConfiguration.getStrictMappingProperties().isApplyStrictMappingConvention());
    }

    @Test
    public void cleanConfig() {
        BeanMapper beanMapper = new BeanMapperBuilder().build(); // Core wrap
        beanMapper = wrapAndSetFieldsForSingleRun(beanMapper);
        beanMapper = wrapAndSetFieldsForSingleRun(beanMapper); // Wrap it twice to make sure we have a parent
                                                               // override wrap with the fields all set
        assertNotNull("Container class must be set", beanMapper.getConfiguration().getCollectionClass());
        assertNotNull("Target class must be set", beanMapper.getConfiguration().getTargetClass());
        assertNotNull("Target must be set", beanMapper.getConfiguration().getTarget());

        beanMapper = beanMapper.clear().build();
        assertNull("Container class must be cleared", beanMapper.getConfiguration().getCollectionClass());
        assertNull("Target class must be cleared", beanMapper.getConfiguration().getTargetClass());
        assertNull("Target must be cleared", beanMapper.getConfiguration().getTarget());
    }

    private BeanMapper wrapAndSetFieldsForSingleRun(BeanMapper beanMapper) {
        return beanMapper
                .wrap()
                .setTargetClass(String.class)
                .setTarget("Hello world!")
                .setCollectionClass(String.class)
                .build(); // Wrap in an override wrap
    }

}
