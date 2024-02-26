package com.pos.javapos.helper;

public record ApiResponse(boolean success, String message, Object data) {

}
