package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.List;
import com.toedter.calendar.JDateChooser;
import entity.NhanVien;
import dao.ThongKeMonAn_DAO;

public class ThongKeMonAn_GUI extends JPanel implements ActionListener {
//    private MenuBar_GUI menuBar;
//    private NhanVien currentUser;
    private ThongKeMonAn_DAO tkDAO;

    // UI Components
    private JComboBox<String> monthCombo;
    private JComboBox<String> yearCombo;
    private JComboBox<String> typeCombo;
    private JComboBox<String> topCombo;
    private JDateChooser startDateChooser;
    private JDateChooser endDateChooser;
    private JButton btnViewPieChart;
    private JButton btnViewBarChart;
    private JButton btnExportData;
    private JLabel timeLabel;

    // Chart components
    private JPanel pieChartPanel;
    private JPanel barChartPanel;
    private JPanel summaryPanel;
    private JPanel pieChartContainer;
    private JPanel barChartContainer;
    
    // Summary labels
    private JLabel lblTongDoanhThu;
    private JLabel lblMonBanChay;
    private JLabel lblLoaiUaThich;

    // Colors
    private final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private final Color ACCENT_COLOR = new Color(231, 76, 60);
    private final Color BACKGROUND_COLOR = new Color(236, 240, 241);

    private DecimalFormat currencyFormat = new DecimalFormat("#,###,### VND");

    public ThongKeMonAn_GUI() {
//        setTitle("Thống Kê Món Ăn - Hệ Thống Quản Lý Nhà Hàng");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setExtendedState(JFrame.MAXIMIZED_BOTH);

//        this.currentUser = loggedInUser;
        this.tkDAO = new ThongKeMonAn_DAO();

//        menuBar = new MenuBar_GUI(currentUser);
//        menuBar.addActionListeners(this);
//        add(menuBar, BorderLayout.NORTH);

        initUI();
        loadInitialData();
        showPieChart();
        showBarChart();
        startTimeUpdate();
        
        setVisible(true);
    }

    private void startTimeUpdate() {
        Timer timer = new Timer(1000, e -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            timeLabel.setText("Thời gian thống kê: " + sdf.format(new Date()));
        });
        timer.start();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        centerPanel.setBackground(BACKGROUND_COLOR);

        pieChartPanel = createPieChartPanel();
        centerPanel.add(pieChartPanel);

        barChartPanel = createBarChartPanel();
        centerPanel.add(barChartPanel);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        summaryPanel = createSummaryPanel();
        mainPanel.add(summaryPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);

        JLabel titleLabel = new JLabel("THỐNG KÊ DOANH THU THEO MÓN ĂN", SwingConstants.CENTER);
        titleLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        headerPanel.add(titleLabel, BorderLayout.NORTH);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        timeLabel = new JLabel("Thời gian thống kê: " + sdf.format(new Date()), SwingConstants.RIGHT);
        timeLabel.setFont(new java.awt.Font("Arial", java.awt.Font.ITALIC, 12));
        headerPanel.add(timeLabel, BorderLayout.SOUTH);

        return headerPanel;
    }

    private JPanel createPieChartPanel() {
        JPanel piePanel = new JPanel(new BorderLayout(0, 10));
        piePanel.setBackground(Color.WHITE);
        piePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel pieTitle = new JLabel("Tỷ lệ doanh thu theo loại món ăn", SwingConstants.CENTER);
        pieTitle.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
        pieTitle.setForeground(PRIMARY_COLOR);
        piePanel.add(pieTitle, BorderLayout.NORTH);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(SECONDARY_COLOR),
            "Bộ lọc thời gian",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new java.awt.Font("Arial", java.awt.Font.BOLD, 12),
            SECONDARY_COLOR
        ));

        filterPanel.add(new JLabel("Tháng:"));
        monthCombo = new JComboBox<>(new String[]{"Tất cả", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"});
        monthCombo.setPreferredSize(new Dimension(80, 25));
        filterPanel.add(monthCombo);

        filterPanel.add(new JLabel("Năm:"));
        int currentYear = LocalDate.now().getYear();
        List<String> years = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            years.add(String.valueOf(currentYear - i));
        }
        yearCombo = new JComboBox<>(years.toArray(new String[0]));
        yearCombo.setPreferredSize(new Dimension(80, 25));
        filterPanel.add(yearCombo);

        btnViewPieChart = new JButton("Xem biểu đồ");
        btnViewPieChart.setBackground(PRIMARY_COLOR);
        btnViewPieChart.setForeground(Color.WHITE);
        btnViewPieChart.setFocusPainted(false);
        btnViewPieChart.addActionListener(this);
        filterPanel.add(btnViewPieChart);

        piePanel.add(filterPanel, BorderLayout.SOUTH);

        pieChartContainer = new JPanel(new BorderLayout());
        pieChartContainer.setBackground(Color.WHITE);
        piePanel.add(pieChartContainer, BorderLayout.CENTER);

        return piePanel;
    }

    private JPanel createBarChartPanel() {
        JPanel barPanel = new JPanel(new BorderLayout(0, 10));
        barPanel.setBackground(Color.WHITE);
        barPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel barTitle = new JLabel("Top món ăn bán chạy nhất", SwingConstants.CENTER);
        barTitle.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
        barTitle.setForeground(PRIMARY_COLOR);
        barPanel.add(barTitle, BorderLayout.NORTH);

        JPanel dateFilterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        dateFilterPanel.setBackground(Color.WHITE);
        dateFilterPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(SECONDARY_COLOR),
            "Khoảng thời gian",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new java.awt.Font("Arial", java.awt.Font.BOLD, 12),
            SECONDARY_COLOR
        ));

        dateFilterPanel.add(new JLabel("Từ ngày:"));
        LocalDate now = LocalDate.now();
        startDateChooser = new JDateChooser(Date.from(now.withDayOfMonth(1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        startDateChooser.setDateFormatString("dd/MM/yyyy");
        startDateChooser.setPreferredSize(new Dimension(120, 25));
        dateFilterPanel.add(startDateChooser);

        dateFilterPanel.add(new JLabel("Đến ngày:"));
        endDateChooser = new JDateChooser(new Date());
        endDateChooser.setPreferredSize(new Dimension(120, 25));
        dateFilterPanel.add(endDateChooser);

        JPanel typeFilterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        typeFilterPanel.setBackground(Color.WHITE);
        typeFilterPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(SECONDARY_COLOR),
            "Tùy chọn hiển thị",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new java.awt.Font("Arial", java.awt.Font.BOLD, 12),
            SECONDARY_COLOR
        ));

        typeFilterPanel.add(new JLabel("Loại:"));
        typeCombo = new JComboBox<>(new String[]{"Doanh thu", "Số lượng bán"});
        typeCombo.setPreferredSize(new Dimension(120, 25));
        typeFilterPanel.add(typeCombo);

        typeFilterPanel.add(new JLabel("Top:"));
        topCombo = new JComboBox<>(new String[]{"5", "10", "15"});
        topCombo.setPreferredSize(new Dimension(60, 25));
        typeFilterPanel.add(topCombo);

        btnViewBarChart = new JButton("Xem biểu đồ");
        btnViewBarChart.setBackground(PRIMARY_COLOR);
        btnViewBarChart.setForeground(Color.WHITE);
        btnViewBarChart.setFocusPainted(false);
        btnViewBarChart.addActionListener(this);
        typeFilterPanel.add(btnViewBarChart);

        JPanel allFilters = new JPanel(new GridLayout(2, 1, 0, 5));
        allFilters.setBackground(Color.WHITE);
        allFilters.add(dateFilterPanel);
        allFilters.add(typeFilterPanel);

        barPanel.add(allFilters, BorderLayout.SOUTH);

        barChartContainer = new JPanel(new BorderLayout());
        barChartContainer.setBackground(Color.WHITE);
        barPanel.add(barChartContainer, BorderLayout.CENTER);

        return barPanel;
    }

    private JPanel createSummaryPanel() {
        JPanel summaryPanel = new JPanel(new BorderLayout(0, 10));
        summaryPanel.setBackground(BACKGROUND_COLOR);
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        statsPanel.setBackground(BACKGROUND_COLOR);

        JPanel card1 = createStatsCard("Tổng doanh thu", "Đang tải...", 
                                       new Color(46, 204, 113), "");
        lblTongDoanhThu = (JLabel) card1.getComponent(1); // value label
        statsPanel.add(card1);

        JPanel card2 = createStatsCard("Món ăn bán chạy nhất", "Đang tải...", 
                                       new Color(52, 152, 219), "");
        lblMonBanChay = (JLabel) card2.getComponent(1);
        statsPanel.add(card2);

        JPanel card3 = createStatsCard("Loại món ăn được ưa thích", "Đang tải...", 
                                       new Color(155, 89, 182), "");
        lblLoaiUaThich = (JLabel) card3.getComponent(1);
        statsPanel.add(card3);

        summaryPanel.add(statsPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        btnExportData = new JButton("Xuất báo cáo");
        btnExportData.setBackground(ACCENT_COLOR);
        btnExportData.setForeground(Color.WHITE);
        btnExportData.setFocusPainted(false);
        btnExportData.addActionListener(this);
        buttonPanel.add(btnExportData);

        summaryPanel.add(buttonPanel, BorderLayout.SOUTH);

        return summaryPanel;
    }

    private JPanel createStatsCard(String title, String value, Color color, String subtitle) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        titleLabel.setForeground(color);
        card.add(titleLabel, BorderLayout.NORTH);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
        card.add(valueLabel, BorderLayout.CENTER);

        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(new java.awt.Font("Arial", java.awt.Font.ITALIC, 11));
        subtitleLabel.setForeground(new Color(100, 100, 100));
        card.add(subtitleLabel, BorderLayout.SOUTH);

        return card;
    }

    private void loadInitialData() {
        // Load dữ liệu ban đầu
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfMonth = today.withDayOfMonth(1);
        
        updateSummaryStats(firstDayOfMonth, today);
    }

    private void updateSummaryStats(LocalDate startDate, LocalDate endDate) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Cập nhật tổng doanh thu
                double tongDoanhThu = tkDAO.getTongDoanhThu(startDate, endDate);
                lblTongDoanhThu.setText(currencyFormat.format(tongDoanhThu));

                // Cập nhật món ăn bán chạy nhất
                String monBanChay = tkDAO.getMonAnBanChayNhat(startDate, endDate);
                lblMonBanChay.setText("<html><div style='text-align: center;'>" + monBanChay + "</div></html>");

                // Cập nhật loại món được ưa thích
                String loaiUaThich = tkDAO.getLoaiMonUaThichNhat(startDate, endDate);
                lblLoaiUaThich.setText("<html><div style='text-align: center;'>" + loaiUaThich + "</div></html>");

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage());
            }
        });
    }

    //Vẽ biểu đồ PieChart
    private void showPieChart() {
        try {
            String selectedMonth = (String) monthCombo.getSelectedItem();
            Integer month = selectedMonth.equals("Tất cả") ? null : Integer.parseInt(selectedMonth);
            int year = Integer.parseInt((String) yearCombo.getSelectedItem());

            Map<String, Double> data = tkDAO.getDoanhThuTheoLoaiMon(month, year);

            pieChartContainer.removeAll();

            if (data.isEmpty()) {
                JLabel noDataLabel = new JLabel("Không có dữ liệu", SwingConstants.CENTER);
                noDataLabel.setFont(new java.awt.Font("Arial", java.awt.Font.ITALIC, 14));
                pieChartContainer.add(noDataLabel);
            } else {
                DefaultPieDataset dataset = new DefaultPieDataset();
                data.forEach(dataset::setValue);

                JFreeChart pieChart = ChartFactory.createPieChart(
                        null, // Không có title
                        dataset,
                        true, true, false
                );

                // Làm đẹp
                pieChart.setTitle((TextTitle) null);
                pieChart.setBackgroundPaint(null);
                pieChart.setAntiAlias(true);
                pieChart.setTextAntiAlias(true);

                RenderingHints rh = new RenderingHints(
                        RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON
                );
                rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                pieChart.setRenderingHints(rh);

                PiePlot plot = (PiePlot) pieChart.getPlot();
                plot.setBackgroundPaint(Color.WHITE);
                plot.setOutlineVisible(false);
                plot.setSectionOutlinesVisible(false);

                // Hiển thị nhãn bên ngoài với nền rõ ràng
                plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} ({1})"));
                plot.setSimpleLabels(false);
                plot.setLabelGap(0.02);
                plot.setLabelBackgroundPaint(new Color(255, 255, 255, 230)); // Nền trắng hơi trong
                plot.setLabelOutlinePaint(Color.GRAY); // Viền nhẹ cho nhãn
                plot.setLabelShadowPaint(Color.LIGHT_GRAY);
                plot.setLabelFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));

                // Vẽ thành ảnh và hiển thị bằng JLabel
                BufferedImage chartImage = pieChart.createBufferedImage(550, 350);
                ImageIcon icon = new ImageIcon(chartImage);
                JLabel chartLabel = new JLabel(icon, JLabel.CENTER);
                pieChartContainer.setLayout(new BorderLayout());
                pieChartContainer.add(chartLabel, BorderLayout.CENTER);
            }

            pieChartContainer.revalidate();
            pieChartContainer.repaint();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi hiển thị biểu đồ tròn: " + e.getMessage());
        }
    }

    //Vẽ biểu đồ BarChart
    private void showBarChart() {
        try {
            Date startDate = startDateChooser.getDate();
            Date endDate = endDateChooser.getDate();
            if (startDate == null || endDate == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn đầy đủ ngày bắt đầu và kết thúc.");
                return;
            }

            LocalDate startLocalDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate endLocalDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int topCount = Integer.parseInt((String) topCombo.getSelectedItem());
            String type = (String) typeCombo.getSelectedItem();

            barChartContainer.removeAll();

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            if (type.equals("Doanh thu")) {
                Map<String, Double> data = tkDAO.getTopMonAnTheoDoanhThu(startLocalDate, endLocalDate, topCount);
                data.forEach((ten, doanhThu) -> dataset.addValue(doanhThu, "Doanh thu (VND)", ten));
            } else {
                Map<String, Integer> data = tkDAO.getTopMonAnTheoSoLuong(startLocalDate, endLocalDate, topCount);
                data.forEach((ten, soLuong) -> dataset.addValue(soLuong, "Số lượng", ten));
            }

            JFreeChart barChart = ChartFactory.createBarChart(
                    "Top " + topCount + " món ăn bán chạy nhất theo " + type.toLowerCase(),
                    "Tên món ăn",
                    type.equals("Doanh thu") ? "Doanh thu (VND)" : "Số lượng",
                    dataset
            );

            //Làm đẹp & rõ nét
            barChart.setTitle(new TextTitle(barChart.getTitle().getText(), new java.awt.Font("Arial", java.awt.Font.BOLD, 18)));
            barChart.setBorderVisible(false);
            barChart.setAntiAlias(true);
            barChart.setTextAntiAlias(true);

            RenderingHints rh = new RenderingHints(
                    RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON
            );
            rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            barChart.setRenderingHints(rh);

            CategoryPlot plot = (CategoryPlot) barChart.getPlot();
            plot.setBackgroundPaint(Color.WHITE);
            plot.setRangeGridlinePaint(new Color(189, 195, 199));
            plot.setOutlineVisible(false);

            BarRenderer renderer = (BarRenderer) plot.getRenderer();
            renderer.setBarPainter(new StandardBarPainter());
            renderer.setSeriesPaint(0, new Color(41, 128, 185));

            plot.getDomainAxis().setTickLabelFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
            plot.getRangeAxis().setTickLabelFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
            plot.getDomainAxis().setCategoryLabelPositions(
                    CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6)
            );

            ChartPanel chartPanel = new ChartPanel(barChart);
            chartPanel.setPreferredSize(new Dimension(450, 350));
            chartPanel.setMaximumDrawWidth(1000);
            chartPanel.setMaximumDrawHeight(800);
            chartPanel.setOpaque(false);
            chartPanel.setDoubleBuffered(true);

            barChartContainer.setLayout(new BorderLayout());
            barChartContainer.add(chartPanel, BorderLayout.CENTER);

            barChartContainer.revalidate();
            barChartContainer.repaint();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi hiển thị biểu đồ cột: " + e.getMessage());
        }
    }

    //Xuất báo cáo File Excel
    private void exportReportToExcel() {
        try {
            Date startDate = startDateChooser.getDate();
            Date endDate = endDateChooser.getDate();
            if (startDate == null || endDate == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn đầy đủ ngày bắt đầu và kết thúc.");
                return;
            }

            LocalDate startLocalDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate endLocalDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            List<Object[]> reportData = tkDAO.getBaoCaoChiTiet(startLocalDate, endLocalDate);
            if (reportData.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không có dữ liệu để xuất báo cáo.");
                return;
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Lưu file báo cáo Excel");
            fileChooser.setSelectedFile(new File("BaoCaoThongKeMonAn.xlsx"));
            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection != JFileChooser.APPROVE_OPTION) return;

            File file = fileChooser.getSelectedFile();
            if (!file.getName().endsWith(".xlsx")) {
                file = new File(file.getAbsolutePath() + ".xlsx");
            }

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Thống kê món ăn");

            // Header
            String[] headers = {"Tên món ăn", "Tên loại", "Số lượng", "Doanh thu (VND)"};
            Row headerRow = sheet.createRow(0);
            CellStyle headerStyle = workbook.createCellStyle();
            Font boldFont = workbook.createFont();
            boldFont.setBold(true);
            boldFont.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(boldFont);
            headerStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Data
            double total = 0;
            int rowNum = 1;
            for (Object[] row : reportData) {
                Row r = sheet.createRow(rowNum++);
                r.createCell(0).setCellValue((String) row[0]);
                r.createCell(1).setCellValue((String) row[1]);
                r.createCell(2).setCellValue((Integer) row[2]);
                r.createCell(3).setCellValue((Double) row[3]);
                total += (Double) row[3];
            }

            // Tổng cộng
            Row totalRow = sheet.createRow(rowNum++);
            Cell c1 = totalRow.createCell(2);
            Cell c2 = totalRow.createCell(3);
            c1.setCellValue("Tổng cộng:");
            c2.setCellValue(total);
            CellStyle boldRight = workbook.createCellStyle();
            boldRight.setFont(boldFont);
            boldRight.setAlignment(HorizontalAlignment.RIGHT);
            c1.setCellStyle(boldRight);
            c2.setCellStyle(boldRight);

            Drawing<?> drawing = sheet.createDrawingPatriarch();
            CreationHelper helper = workbook.getCreationHelper();

            // Biểu đồ tròn
            Map<String, Double> pieData = tkDAO.getDoanhThuTheoLoaiMon(null, startLocalDate.getYear());
            if (!pieData.isEmpty()) {
                DefaultPieDataset pieDataset = new DefaultPieDataset();
                pieData.forEach(pieDataset::setValue);
                JFreeChart pieChart = ChartFactory.createPieChart(null, pieDataset, true, true, false);
                PiePlot plot = (PiePlot) pieChart.getPlot();
                plot.setOutlineVisible(false);
                plot.setBackgroundPaint(Color.WHITE);
                BufferedImage chartImage = pieChart.createBufferedImage(500, 300);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(chartImage, "png", baos);
                int pictureIdx = workbook.addPicture(baos.toByteArray(), Workbook.PICTURE_TYPE_PNG);
                ClientAnchor anchor = helper.createClientAnchor();
                anchor.setCol1(0);
                anchor.setRow1(rowNum + 2);
                Picture pict = drawing.createPicture(anchor, pictureIdx);
                pict.resize();
            }

            // Biểu đồ cột
            DefaultCategoryDataset barDataset = new DefaultCategoryDataset();

            // Lấy loại thống kê và số lượng top từ giao diện
            String type = typeCombo.getSelectedItem().toString();
            int topCount = Integer.parseInt((String) topCombo.getSelectedItem());

            if (type.equals("Doanh thu")) {
            	Map<String, Double> data = tkDAO.getTopMonAnTheoDoanhThu(startLocalDate, endLocalDate, topCount);
            	data.forEach((ten, value) -> barDataset.addValue(value, "Doanh thu", ten));
            } else {
            	Map<String, Integer> data = tkDAO.getTopMonAnTheoSoLuong(startLocalDate, endLocalDate, topCount);
            	data.forEach((ten, value) -> barDataset.addValue(value, "Số lượng", ten));
            }

            if (!barDataset.getColumnKeys().isEmpty()) {
                JFreeChart barChart = ChartFactory.createBarChart(
                        null,
                        "Tên món",
                        type.equals("Doanh thu") ? "Doanh thu (VND)" : "Số lượng",
                        barDataset
                );

                barChart.setBackgroundPaint(Color.WHITE);
                CategoryPlot bPlot = (CategoryPlot) barChart.getPlot();
                bPlot.setBackgroundPaint(Color.WHITE);
                bPlot.setOutlineVisible(false);

                BarRenderer renderer = (BarRenderer) bPlot.getRenderer();
                renderer.setSeriesPaint(0, new Color(41, 128, 185));
                renderer.setBarPainter(new StandardBarPainter());

                BufferedImage barImage = barChart.createBufferedImage(500, 300);
                ByteArrayOutputStream barOut = new ByteArrayOutputStream();
                ImageIO.write(barImage, "png", barOut);
                int barPictureIdx = workbook.addPicture(barOut.toByteArray(), Workbook.PICTURE_TYPE_PNG);
                ClientAnchor barAnchor = helper.createClientAnchor();
                barAnchor.setCol1(0);
                barAnchor.setRow1(rowNum + 25);
                Picture barPict = drawing.createPicture(barAnchor, barPictureIdx);
                barPict.resize();
            }

            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            try (FileOutputStream out = new FileOutputStream(file)) {
                workbook.write(out);
                workbook.close();
            }

            JOptionPane.showMessageDialog(this, "Xuất báo cáo Excel thành công!");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xuất báo cáo: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnViewPieChart) {
            showPieChart();
        } else if (source == btnViewBarChart) {
            showBarChart();
        } else if (source == btnExportData) {
            exportReportToExcel();
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Tạo JFrame test
            JFrame frame = new JFrame("Test Thống Kê Món Ăn");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 700); // hoặc frame.pack() nếu bạn dùng layout co giãn

            // Add panel vào frame
            ThongKeMonAn_GUI thongKePanel = new ThongKeMonAn_GUI();
            frame.setContentPane(thongKePanel);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}