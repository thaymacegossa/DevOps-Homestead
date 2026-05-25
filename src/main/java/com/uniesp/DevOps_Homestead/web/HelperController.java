package com.uniesp.DevOps_Homestead.web;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelperController {

    private static String FLASH_MESSAGE = "mensagem";
    private static String FLASH_TYPE = "tipo";
    private static String TIPO_SUCESSO = "sucesso";
    private static String TIPO_ERRO = "erro";

    @FunctionalInterface
    public interface ThrowingRunnable {
        void run() throws Exception;
    }

    public static String criarComRedirecionamento(ThrowingRunnable action,
            String successMessage,
            String errorMessagePrefix,
            String successRedirect,
            String errorRedirect,
            RedirectAttributes redirectAttributes) {
        try {
            action.run();
            redirectAttributes.addFlashAttribute(FLASH_MESSAGE, successMessage);
            redirectAttributes.addFlashAttribute(FLASH_TYPE, TIPO_SUCESSO);
            return successRedirect;
        } catch (Exception e) {
            log.error(errorMessagePrefix, e);
            redirectAttributes.addFlashAttribute(FLASH_MESSAGE, errorMessagePrefix + ": " + e.getMessage());
            redirectAttributes.addFlashAttribute(FLASH_TYPE, TIPO_ERRO);
            return errorRedirect;
        }
    }

    @FunctionalInterface
    public interface ErrorRedirectProvider {
        String getErrorRedirect();
    }

    public static String atualizarComRedirecionamento(ThrowingRunnable action,
            String successMessage,
            String errorMessagePrefix,
            String successRedirect,
            ErrorRedirectProvider errorRedirectProvider,
            RedirectAttributes redirectAttributes) {
        try {
            action.run();
            redirectAttributes.addFlashAttribute(FLASH_MESSAGE, successMessage);
            redirectAttributes.addFlashAttribute(FLASH_TYPE, TIPO_SUCESSO);
            return successRedirect;
        } catch (Exception e) {
            log.error(errorMessagePrefix, e);
            redirectAttributes.addFlashAttribute(FLASH_MESSAGE, errorMessagePrefix + ": " + e.getMessage());
            redirectAttributes.addFlashAttribute(FLASH_TYPE, TIPO_ERRO);
            return errorRedirectProvider.getErrorRedirect();
        }
    }

    public static String deletarComRedirecionamento(ThrowingRunnable action,
            String successMessage,
            String errorMessagePrefix,
            String redirectPadrao,
            RedirectAttributes redirectAttributes) {
        try {
            action.run();
            redirectAttributes.addFlashAttribute(FLASH_MESSAGE, successMessage);
            redirectAttributes.addFlashAttribute(FLASH_TYPE, TIPO_SUCESSO);
        } catch (Exception e) {
            log.error(errorMessagePrefix, e);
            redirectAttributes.addFlashAttribute(FLASH_MESSAGE, errorMessagePrefix + ": " + e.getMessage());
            redirectAttributes.addFlashAttribute(FLASH_TYPE, TIPO_ERRO);
        }
        return redirectPadrao;
    }

}
