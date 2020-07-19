package com.murillo.algafood.infra.service.report;

import com.murillo.algafood.domain.filter.VendaDiariaFilter;
import com.murillo.algafood.domain.service.VendaQueryService;
import com.murillo.algafood.domain.service.VendaReportService;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;

@Service
public class PdfVendaReportServiceImpl implements VendaReportService {

    @Autowired
    private VendaQueryService vendaQueryService;

    @Override
    public byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {

        try {
            var inputStream = this.getClass().getResourceAsStream("/relatorios/vendas-diarias.jasper");

            var parametros = new HashMap<String, Object>();
            parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));

            var vendasDiarias = new JRBeanCollectionDataSource(vendaQueryService.consultarVendasDiarias(filtro, timeOffset));

            var jasperPrint = JasperFillManager.fillReport(inputStream, parametros, vendasDiarias);

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new ReportException("Não foi possível emitir relatório de vendas diárias", e.getCause());
        }
    }
}
