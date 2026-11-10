package com.dropsense.DropsenseProject.service;

import com.dropsense.DropsenseProject.dto.RiskResult;
import com.dropsense.DropsenseProject.entity.RiskLevel;
import com.dropsense.DropsenseProject.entity.Student;

import org.springframework.stereotype.Service;

@Service
public class RiskService
{
    // Đánh giá nguy cơ bỏ học dựa trên thông tin sinh viên
    public RiskResult evaluateRisk(Student student)
    {
        if (student == null)
        {
            throw new IllegalArgumentException("Thông tin sinh viên không hợp lệ!");
        }

        int riskScore = 0;
        String reasons = "";
        String recommendations = "";

        // 1. Kiểm tra điểm GPA tích lũy
        if (student.getGpa() < 2.0)
        {
            riskScore += 30;

            reasons += "- Điểm GPA tích lũy dưới 2.0.\n";
            recommendations += "- Cần lập kế hoạch cải thiện kết quả học tập.\n";
        }

        // 2. Kiểm tra mức giảm GPA giữa hai học kỳ
        double gpaDecrease = student.getPreviousGpa() - student.getCurrentGpa();

        if (gpaDecrease >= 0.5)
        {
            riskScore += 20;

            reasons += "- Điểm GPA giảm từ 0.5 điểm trở lên.\n";
            recommendations += "- Cần tìm nguyên nhân khiến kết quả học tập giảm sút.\n";
        }

        // 3. Kiểm tra số học phần trượt
        if (student.getFailedCourses() >= 3)
        {
            riskScore += 20;

            reasons += "- Sinh viên trượt từ 3 học phần trở lên.\n";
            recommendations += "- Nên đăng ký học lại và xây dựng kế hoạch ôn tập.\n";
        }

        // 4. Kiểm tra tỷ lệ chuyên cần
        if (student.getAttendanceRate() < 70)
        {
            riskScore += 20;

            reasons += "- Tỷ lệ chuyên cần dưới 70%.\n";
            recommendations += "- Cần cải thiện việc tham gia các buổi học.\n";
        }

        // 5. Kiểm tra tỷ lệ hoàn thành tín chỉ
        if (student.getCreditCompletionRate() < 50)
        {
            riskScore += 10;

            reasons += "- Tỷ lệ hoàn thành tín chỉ dưới 50%.\n";
            recommendations += "- Nên gặp cố vấn học tập để rà soát tiến độ học.\n";
        }

        // 6. Xác định mức nguy cơ
        RiskLevel riskLevel;

        if (riskScore >= 60)
        {
            riskLevel = RiskLevel.HIGH;
        }
        else if (riskScore >= 30)
        {
            riskLevel = RiskLevel.MEDIUM;
        }
        else
        {
            riskLevel = RiskLevel.LOW;
        }

        // 7. Trường hợp không phát hiện dấu hiệu cảnh báo
        if (riskScore == 0)
        {
            reasons = "Chưa phát hiện dấu hiệu nguy cơ theo bộ quy tắc hiện tại.";
            recommendations = "Tiếp tục duy trì kết quả học tập và chuyên cần.";
        }

        // 8. Trả về kết quả đánh giá
        return new RiskResult(riskScore, riskLevel, reasons, recommendations);
    }
}
