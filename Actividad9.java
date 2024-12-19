import java.util.regex.*;
import java.util.concurrent.*;

// Clase que implementa la validación de contraseñas
class ValidadorContraseñas implements Runnable {
    private String contraseña;

    public ValidadorContraseñas(String contraseña) {
        this.contraseña = contraseña;
    }

    @Override
    public void run() {
        if (validarContraseña(contraseña)) {
            System.out.println("La contraseña '" + contraseña + "' es válida.");
        } else {
            System.out.println("La contraseña '" + contraseña + "' no es válida.");
        }
    }

    // Método que valida la contraseña utilizando expresiones regulares
    private boolean validarContraseña(String contraseña) {
        // Expresión regular para validar los requisitos de la contraseña
        String patron = "^(?=.*[A-Z].*[A-Z])(?=.*[a-z].*[a-z].*[a-z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$";

        // Crear el patrón y el matcher
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(contraseña);

        return matcher.matches();
    }
}

public class Actividad9 {
    public static void main(String[] args) throws InterruptedException {
        // Lista de contraseñas a validar
        String[] contraseñas = {
                "Abcdef1@",
                "12345@Axyz",
                "P@ssw0rd123",
                "short",
                "Good@Password1"
        };

        // Crear un pool de hilos para ejecutar la validación concurrentemente
        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (String contraseña : contraseñas) {
            ValidadorContraseñas validador = new ValidadorContraseñas(contraseña);
            executor.submit(validador);  // Enviar cada validación a un hilo del pool
        }

        // Apagar el executor después de que todas las tareas se completen
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);  // Esperar un minuto para que finalicen todos los hilos
    }
}