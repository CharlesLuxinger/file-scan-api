package com.github.charlesluxinger.api.model;

public class RegexPattern {

	public final static String OR = "|";
	public final static String NUMBER_REGEX = "[0-9]+";
	public final static String DOUBLE_REGEX = NUMBER_REGEX + "\\." + NUMBER_REGEX;
	public final static String BYTES_QUANTITY_REGEX = NUMBER_REGEX + "\\s+Bytes";
	public final static String K_BYTES_QUANTITY_REGEX = DOUBLE_REGEX + "\\s+KB";
	public final static String BYTES_QUANTITY_REGEX_OR_K_BYTES_QUANTITY_REGEX = BYTES_QUANTITY_REGEX + OR + K_BYTES_QUANTITY_REGEX;
	public final static String LINE_QUANTITY_REGEX = NUMBER_REGEX + "\\s+lines";
	public final static String LINE_REGEX = LINE_QUANTITY_REGEX + OR + BYTES_QUANTITY_REGEX + OR + K_BYTES_QUANTITY_REGEX;
	public final static String PATH_REGEX = "[A-Za-z0-9.-_-]+";


}
