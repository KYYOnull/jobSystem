package com.easyjob.enums;

public enum ImportTemplateTypeEnum {

    QUESTION(0, "/template/template_question.xlsx", "问题模板.xlsx"),
    QUESTION_EXAM(1, "template/template_exam.xlsx", "试题库模板.xlsx");

    private final int type;
    private final String templatePath;
    private final String templateName;

    ImportTemplateTypeEnum(int type, String templatePath, String templateName) {
        this.type= type;
        this.templatePath = templatePath;
        this.templateName = templateName;
    }

    public static ImportTemplateTypeEnum getByType(Integer type) {
        for (ImportTemplateTypeEnum it : ImportTemplateTypeEnum.values()) {
            if (it.type == type) {
                return it;
            }
        }
        return null;
    }

    public String getTemplateName() {
        return templateName;
    }

    public String getTemplatePath() {
        return templatePath;
    }
}
