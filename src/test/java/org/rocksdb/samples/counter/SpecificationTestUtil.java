package org.rocksdb.samples.counter;

import org.junit.Assert;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.assertj.core.api.Assertions.assertThat;

public class SpecificationTestUtil {

  /**
   * Verifies if clazz is written as required by specification.
   *
   * @param clazz Class to check
   *
   * @throws NoSuchMethodException
   * @throws InvocationTargetException
   * @throws InstantiationException
   * @throws IllegalAccessException
   */
  public static void assertUtilityClassWellDefined(final Class<?> clazz)
      throws NoSuchMethodException, InvocationTargetException,
      InstantiationException, IllegalAccessException {
    assertThat(Modifier.isFinal(clazz.getModifiers())).isTrue();
    assertThat(clazz.getDeclaredConstructors().length).isEqualTo(1);
    final Constructor<?> constructor = clazz.getDeclaredConstructor();
    if (constructor.isAccessible() ||
        !Modifier.isPrivate(constructor.getModifiers())) {
      assertThat(false).isTrue();
    }
    constructor.setAccessible(true);
    constructor.newInstance();
    constructor.setAccessible(false);
    for (final Method method : clazz.getMethods()) {
      if (!Modifier.isStatic(method.getModifiers())
          && method.getDeclaringClass().equals(clazz)) {
        assertThat(false).isTrue();
      }
    }
  }
}
