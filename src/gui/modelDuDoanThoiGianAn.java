package gui;

import java.time.LocalDateTime;

import weka.classifiers.trees.RandomForest;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class modelDuDoanThoiGianAn {

    private static Instances dataset;
    private static RandomForest model;

    public static void khoiTaoModel() {
        try {
            dataset = loadDataset("D:\\HK2-Year3\\PTUD\\DatBan_NhaHang\\thoiGianTBAn.arff");

            if (dataset.classIndex() == -1)
                dataset.setClassIndex(dataset.numAttributes() - 1);

            model = trainRandomForest(dataset);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     //Load dataset từ file ARFF
    public static Instances loadDataset(String filePath) throws Exception {
        DataSource source = new DataSource(filePath);
        return source.getDataSet();
    }

     // Huấn luyện mô hình RandomForest với dữ liệu đã cho.
    public static RandomForest trainRandomForest(Instances trainData) throws Exception {
        RandomForest rf = new RandomForest();
        rf.buildClassifier(trainData);
        return rf;
    }

     // Dự đoán thời gian ăn dựa trên số khách, số món và thời gian bắt đầu.
    public static LocalDateTime duDoanThoiGian(int soKhach, int soMon, LocalDateTime thoiGianBatDau) {
        try {
            if (dataset == null || model == null) {
                khoiTaoModel();
            }

            if (dataset == null || model == null) {
                throw new IllegalStateException("Dataset hoặc model vẫn null sau khi khởi tạo!");
            }

            // Copy mẫu đầu tiên để làm mẫu mới
            Instance newInstance = (Instance) dataset.firstInstance().copy();

            // Gán giá trị mới
            newInstance.setValue(0, soKhach);
            newInstance.setValue(1, soMon);

            // Dự đoán số phút
            double soPhut = model.classifyInstance(newInstance);

            return thoiGianBatDau.plusMinutes((long) soPhut);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

