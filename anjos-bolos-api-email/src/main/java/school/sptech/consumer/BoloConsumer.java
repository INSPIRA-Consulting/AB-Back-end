package school.sptech.consumer;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import school.sptech.dto.BoloDTO;
import school.sptech.dto.PedidoBoloDTO;

@Component
public class BoloConsumer {

    private final JavaMailSender mailSender;

    @Value("${spring.encomenda.mail.to}")
    private String to;

    @Value("${spring.encomenda.mail.username}")
    private String from;

    public BoloConsumer(@Qualifier("encomendaMailSender") JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @RabbitListener(queues = "${broker.encomenda.queue.name}")
    public void consumirMensagem(BoloDTO bolo) {
        System.out.println("Recebido da fila: " + bolo);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject("Resumo de encomendas");
            helper.setText(buildEmailBody(bolo), true);

            mailSender.send(message);
            System.out.println("E-mail enviado com sucesso!");

        } catch (MessagingException | MailException e) {
            System.err.println("Erro ao enviar e-mail: " + e.getMessage());
        }
    }

    private String buildEmailBody(BoloDTO dto) {
        PedidoBoloDTO pedido = dto != null ? dto.pedido() : null;
        int totalEncomendas = pedido == null ? 0 : 1;
        String dataReferencia = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        List<String> massas = pedido != null && pedido.massas() != null ? pedido.massas() : Collections.emptyList();
        List<String> coberturas = pedido != null && pedido.coberturas() != null ? pedido.coberturas() : Collections.emptyList();
        List<String> recheios = pedido != null && pedido.recheios() != null ? pedido.recheios() : Collections.emptyList();
        int totalCombos = massas.size() + coberturas.size() + recheios.size();

        String destaqueMassa = massas.isEmpty() ? "Sem massa" : safeTexto(massas.get(0));
        String destaqueCobertura = coberturas.isEmpty() ? "-" : safeTexto(coberturas.get(0));
        String destaqueRecheio = recheios.isEmpty() ? "-" : safeTexto(recheios.get(0));
        String destaqueObservacao = pedido == null ? "Sem observações" : safeTexto(pedido.observacao());

        Double pesoPedido = pedido != null ? pedido.pesoKg() : null;
        double pesoTotal = pesoPedido == null ? 0d : pesoPedido;

        String tabelaDetalhes = buildDetalhesTabela(massas, coberturas, recheios, pedido);

        StringBuilder html = new StringBuilder();
        String heroImageUrl = "https://images.unsplash.com/photo-1517430816045-df4b7de11d1d?auto=format&fit=crop&w=900&q=80";
        html.append("<!DOCTYPE html>\n")
            .append("<html lang=\"pt-BR\">\n")
            .append("<head>\n")
            .append("  <meta charSet=\"UTF-8\" />\n")
            .append("  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n")
            .append("  <title>Anjos Bolos · Resumo de Encomendas</title>\n")
            .append("</head>\n")
            .append("<body style=\"margin:0;padding:32px;background:#F7F7F7;font-family:'Montserrat','Segoe UI',Roboto,'Helvetica Neue',sans-serif;color:#432310;\">\n")
            .append("  <table role=\"presentation\" style=\"width:100%;max-width:760px;margin:0 auto;background:#ffffff;border-radius:28px;border:1px solid #E8DDD6;box-shadow:0 18px 45px rgba(86,39,11,0.08);overflow:hidden;\">\n")
            .append("    <tr>\n")
            .append("      <td style=\"padding:0;\">\n")
            .append("        <div style=\"display:flex;flex-wrap:wrap;\">\n")
            .append("          <div style=\"flex:1 1 320px;padding:36px;\">\n")
            .append("            <div style=\"display:flex;align-items:center;gap:12px;margin-bottom:16px;\">\n")
            .append("              <img src=\"https://i.imgur.com/Y6Hh7dD.png\" alt=\"Logo Anjos Bolos\" style=\"width:48px;height:48px;border-radius:14px;border:2px solid #F9C9A4;object-fit:cover;\"/>\n")
            .append("              <div>\n")
            .append("                <p style=\"margin:0;font-size:12px;letter-spacing:0.08em;text-transform:uppercase;color:#D98A00;font-weight:600;\">Painel Anjos Bolos</p>\n")
            .append("                <h1 style=\"margin:4px 0 0;font-size:26px;color:#663B2B;\">Resumo de Encomendas</h1>\n")
            .append("              </div>\n")
            .append("            </div>\n")
            .append("            <p style=\"margin:0;color:#6B4A3C;font-size:15px;line-height:1.6;\">Confira abaixo as encomendas registradas nas últimas horas e compartilhe com a cozinha.</p>\n")
            .append("            <div style=\"margin-top:22px;display:flex;gap:12px;flex-wrap:wrap;\">\n")
            .append(buildBadge("Massa destaque", destaqueMassa))
            .append(buildBadge("Cobertura", destaqueCobertura))
            .append(buildBadge("Recheio", destaqueRecheio))
            .append("            </div>\n")
            .append("          </div>\n")
            .append("          <div style=\"flex:1 1 260px;min-height:220px;background:#FDE4DB url('")
            .append(heroImageUrl)
            .append("') center/cover no-repeat;display:flex;align-items:flex-end;justify-content:flex-end;\">\n")
            .append("            <div style=\"background:rgba(255,255,255,0.85);margin:16px;border-radius:16px;padding:14px 18px;color:#663B2B;\">\n")
            .append("              <p style=\"margin:0;font-size:12px;letter-spacing:0.08em;text-transform:uppercase;\">Período</p>\n")
            .append("              <p style=\"margin:2px 0 0;font-size:18px;font-weight:700;\">")
            .append(dataReferencia)
            .append("</p>\n")
            .append("            </div>\n")
            .append("          </div>\n")
            .append("        </div>\n")
            .append("      </td>\n")
            .append("    </tr>\n")
            .append("    <tr>\n")
            .append("      <td style=\"padding:28px 32px 0;\">\n")
            .append("        <table role=\"presentation\" style=\"width:100%;border-collapse:separate;border-spacing:18px 0;\">\n")
            .append("          <tr>\n")
            .append(buildMetricCard("Encomendas", String.valueOf(totalEncomendas), "Registradas", "#663B2B", "#F4D6C2"))
            .append(buildMetricCard("Variedades", String.valueOf(totalCombos), "Combinações únicas", "#7A3B17", "#F7E7DA"))
            .append(buildMetricCard("Peso total", formatPesoResumo(pesoTotal), "kg informados", "#C25B43", "#FFE4DB"))
            .append("          </tr>\n")
            .append("        </table>\n")
            .append("      </td>\n")
            .append("    </tr>\n")
            .append("    <tr>\n")
            .append("      <td style=\"padding:28px 32px;\">\n")
            .append("        <div style=\"background:#FDF8F4;border:1px solid #EDD9CE;border-radius:18px;padding:16px 22px;margin-bottom:18px;\">\n")
            .append("          <p style=\"margin:0;font-size:12px;text-transform:uppercase;letter-spacing:0.08em;color:#C25B43;font-weight:600;\">Observação em destaque</p>\n")
            .append("          <p style=\"margin:6px 0 0;color:#5a3728;font-size:15px;line-height:1.6;\">")
            .append(destaqueObservacao)
            .append("</p>\n")
            .append("        </div>\n")
            .append(tabelaDetalhes)
            .append("        <p style=\"margin:18px 0 0;color:#876555;font-size:12px;text-align:center;\">Dashboard inspirado na nova interface Anjos Bolos.</p>\n")
            .append("      </td>\n")
            .append("    </tr>\n")
            .append("  </table>\n")
            .append("</body>\n")
            .append("</html>");

        return html.toString();
    }

    private String formatPeso(Double peso) {
        if (peso == null) {
            return "Peso não informado";
        }
        return String.format("%.2f kg", peso);
    }

    private String safeTexto(String valor) {
        return valor == null || valor.isBlank() ? "Não informado" : valor;
    }

    private String formatPesoResumo(double peso) {
        String valor = peso <= 0 ? "0,00" : String.format("%.2f", peso).replace('.', ',');
        return valor + " kg";
    }

    private String buildBadge(String titulo, String valor) {
        return new StringBuilder()
                .append("            <div style=\"background:#F7E7DA;border-radius:30px;padding:10px 16px;display:flex;flex-direction:column;gap:2px;\">")
                .append("<span style=\"font-size:11px;letter-spacing:0.08em;text-transform:uppercase;color:#A35C3A;font-weight:600;\">")
                .append(titulo)
                .append("</span><strong style=\"font-size:14px;color:#432310;\">")
                .append(valor)
                .append("</strong></div>\n")
                .toString();
    }

    private String buildMetricCard(String titulo, String valor, String legenda, String corTexto, String corFundo) {
        return new StringBuilder()
                .append("            <td style=\"background:")
                .append(corFundo)
                .append(";border-radius:20px;padding:20px;min-width:150px;\">\n")
                .append("              <p style=\"margin:0;font-size:12px;letter-spacing:0.08em;text-transform:uppercase;color:")
                .append(corTexto)
                .append(";font-weight:600;\">")
                .append(titulo)
                .append("</p>\n")
                .append("              <p style=\"margin:6px 0 2px;font-size:26px;font-weight:700;color:")
                .append(corTexto)
                .append(";\">")
                .append(valor)
                .append("</p>\n")
                .append("              <p style=\"margin:0;font-size:13px;color:#6B4A3C;\">")
                .append(legenda)
                .append("</p>\n")
                .append("            </td>\n")
                .toString();
    }

    private String buildDetalhesTabela(List<String> massas, List<String> coberturas, List<String> recheios, PedidoBoloDTO pedido) {
        StringBuilder tabela = new StringBuilder();
        tabela.append("        <table role=\"presentation\" style=\"width:100%;border-collapse:collapse;border-radius:18px;overflow:hidden;box-shadow:0 6px 18px rgba(0,0,0,0.06);\">\n")
                .append("          <thead>\n")
                .append("            <tr style=\"background:#663B2B;color:#fff;\">\n")
                .append("              <th style=\"padding:14px;font-size:13px;letter-spacing:0.08em;text-transform:uppercase;\">Massas</th>\n")
                .append("              <th style=\"padding:14px;font-size:13px;letter-spacing:0.08em;text-transform:uppercase;\">Coberturas</th>\n")
                .append("              <th style=\"padding:14px;font-size:13px;letter-spacing:0.08em;text-transform:uppercase;\">Recheios</th>\n")
                .append("              <th style=\"padding:14px;font-size:13px;letter-spacing:0.08em;text-transform:uppercase;\">Peso</th>\n")
                .append("              <th style=\"padding:14px;font-size:13px;letter-spacing:0.08em;text-transform:uppercase;\">Observação</th>\n")
                .append("            </tr>\n")
                .append("          </thead>\n")
                .append("          <tbody style=\"background:#fff;\">\n");

        if (pedido == null) {
            tabela.append("            <tr><td colspan=\"5\" style=\"padding:16px;text-align:center;color:#7a6d82;\">Nenhuma encomenda recebida.</td></tr>\n");
        } else {
            tabela.append("            <tr>\n")
                    .append("              <td style=\"padding:16px;border-bottom:1px solid #f0eef2;\">")
                    .append(buildChipList(massas))
                    .append("</td>\n")
                    .append("              <td style=\"padding:16px;border-bottom:1px solid #f0eef2;\">")
                    .append(buildChipList(coberturas))
                    .append("</td>\n")
                    .append("              <td style=\"padding:16px;border-bottom:1px solid #f0eef2;\">")
                    .append(buildChipList(recheios))
                    .append("</td>\n")
                    .append("              <td style=\"padding:16px;text-align:center;border-bottom:1px solid #f0eef2;font-weight:600;color:#3f2215;\">")
                    .append(formatPeso(pedido.pesoKg()))
                    .append("</td>\n")
                    .append("              <td style=\"padding:16px;border-bottom:1px solid #f0eef2;color:#6b4a3c;font-size:14px;\">")
                    .append(safeTexto(pedido.observacao()))
                    .append("</td>\n")
                    .append("            </tr>\n");
        }

        tabela.append("          </tbody>\n")
                .append("        </table>\n");
        return tabela.toString();
    }

    private String buildChipList(List<String> valores) {
        List<String> pontos = valores == null ? Collections.emptyList() : valores;
        if (pontos.isEmpty()) {
            return "<span style=\"display:inline-block;background:#EFE5DE;color:#7a5a4a;padding:6px 14px;border-radius:999px;font-size:13px;\">Não informado</span>";
        }

        StringBuilder chips = new StringBuilder();
        for (String valor : pontos) {
            chips.append("<span style=\"display:inline-block;background:#EFE5DE;color:#7a5a4a;padding:6px 14px;border-radius:999px;font-size:13px;margin:3px;\">")
                    .append(safeTexto(valor))
                    .append("</span>");
        }
        return chips.toString();
    }
}