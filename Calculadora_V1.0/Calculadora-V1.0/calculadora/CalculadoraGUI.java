package calculadora;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalculadoraGUI extends JFrame implements ActionListener {
    private JTextField campoTexto;
    private StringBuilder operacion = new StringBuilder();

    public CalculadoraGUI() {
        setTitle("Calculadora Naranja");
        setSize(300, 420);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        campoTexto = new JTextField();
        campoTexto.setFont(new Font("Arial", Font.BOLD, 24));
        campoTexto.setHorizontalAlignment(JTextField.RIGHT);
        campoTexto.setEditable(false);
        campoTexto.setBackground(new Color(255, 224, 178));
        add(campoTexto, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new GridLayout(5, 4, 5, 5));
        panelBotones.setBackground(new Color(255, 204, 128));

        String[] botones = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "C"
        };

        for (String texto : botones) {
            JButton boton = new JButton(texto);
            boton.setFont(new Font("Arial", Font.BOLD, 20));
            boton.setBackground(new Color(255, 152, 0));
            boton.setForeground(Color.WHITE);
            boton.addActionListener(this);
            panelBotones.add(boton);
        }

        add(panelBotones, BorderLayout.CENTER);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String input = e.getActionCommand();

        if (input.matches("[0-9\\.]")) {
            operacion.append(input);
            campoTexto.setText(operacion.toString());
        } else if (input.matches("[+\\-*/]")) {
            if (operacion.length() > 0 && !endsWithOperator()) {
                operacion.append(" ").append(input).append(" ");
                campoTexto.setText(operacion.toString());
            }
        } else if (input.equals("=")) {
            try {
                String resultado = evaluar(operacion.toString());
                campoTexto.setText(resultado);
                operacion.setLength(0); // limpia la operación
            } catch (Exception ex) {
                campoTexto.setText("Error");
                operacion.setLength(0);
            }
        } else if (input.equals("C")) {
            campoTexto.setText("");
            operacion.setLength(0);
        }
    }

    private boolean endsWithOperator() {
        return operacion.toString().matches(".*[+\\-*/]\\s?$");
    }

    private String evaluar(String operacion) throws Exception {
        String[] tokens = operacion.trim().split(" ");
        if (tokens.length != 3)
            throw new Exception("Formato inválido");

        double num1 = Double.parseDouble(tokens[0]);
        String operador = tokens[1];
        double num2 = Double.parseDouble(tokens[2]);
        double resultado;

        switch (operador) {
            case "+": resultado = num1 + num2; break;
            case "-": resultado = num1 - num2; break;
            case "*": resultado = num1 * num2; break;
            case "/":
                if (num2 == 0) throw new ArithmeticException();
                resultado = num1 / num2; break;
            default: throw new Exception("Operador inválido");
        }

        return String.valueOf(resultado);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CalculadoraGUI::new);
    }
}