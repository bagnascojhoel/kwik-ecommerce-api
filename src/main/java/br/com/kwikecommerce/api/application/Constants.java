package br.com.kwikecommerce.api.application;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Locale;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class Constants {

    public static final Locale LOCALE_PT_BR = new Locale("pt", "BR");

}
