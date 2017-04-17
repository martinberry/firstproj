package com.ztravel.product.back.pieces.utils;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.ztravel.common.enums.AdditionalRule;
import com.ztravel.common.enums.CareerType;
import com.ztravel.product.back.freetravel.utils.CharCounter;
import com.ztravel.product.po.pieces.common.BasicInfo;
import com.ztravel.product.po.pieces.common.PriceInfo;
import com.ztravel.product.po.pieces.unvisa.UnVisaDetailInfo;
import com.ztravel.product.po.pieces.unvisa.UnVisaProduct;
import com.ztravel.product.po.pieces.visa.VisaDetailInfo;
import com.ztravel.product.po.pieces.visa.VisaMaterial;
import com.ztravel.product.po.pieces.visa.VisaProcess;
import com.ztravel.product.po.pieces.visa.VisaProduct;

public class PiecesCheckValidator {

    //private static final String serviceTimeReg = "^(([0-9]|[1-9][0-9])(\\.[0-9]{0,1})?|100(\\.0)?)$";
    private static final String priceReg = "^(([1-9][0-9]{0,4}|0)(\\.[0-9]{0,2})?)$";

    public static void AssertVisaProduct(VisaProduct visaProduct) throws IllegalArgumentException {

        //基本信息
        AssertBasicInfo(visaProduct.getBasicInfo());

        //详情信息
        AssertVisaDetailInfo(visaProduct.getDetailInfo());

        //价格信息
        AssertPriceInfo(visaProduct.getPriceInfos());

        //附加信息
        AssertVisaAdditionalInfo(visaProduct.getAdditionalInfos());

        //签证材料
        AssertMaterias(visaProduct.getMaterias());

        //办理流程
        AssertProcesses(visaProduct.getProcesses());

    }

    public static void AssertUnVisaProduct(UnVisaProduct unVisaProduct) throws IllegalArgumentException {

        //基本信息
        AssertBasicInfo(unVisaProduct.getBasicInfo());

        //详情信息
        AssertUnVisaDetailInfo(unVisaProduct.getDetailInfo());

        //价格信息
        AssertPriceInfo(unVisaProduct.getPriceInfos());

        //附加信息
        AssertUnVisaAdditionalInfo(unVisaProduct.getAdditionalInfos());

    }

    private static void AssertProcesses(List<VisaProcess> processes) {
        if (CollectionUtils.isEmpty(processes)) {
            throw new IllegalArgumentException("办理流程不能为空");
        } else {
            for (VisaProcess visaProcess : processes) {
                if (visaProcess.getTitle() == null) {
                    throw new IllegalArgumentException("办理流程标题不能为空");
                } else if (CharCounter.getLength(visaProcess.getTitle()) > 30 || CharCounter.getLength(visaProcess.getTitle())<1) {
                    throw new IllegalArgumentException("办理流程标题长度限制1-30个字符");
                }
                if (visaProcess.getContent() == null) {
                    throw new IllegalArgumentException("办理流程内容不能为空");
                } else if (CharCounter.getLength(visaProcess.getContent()) > 5000 || CharCounter.getLength(visaProcess.getContent())<1) {
                    throw new IllegalArgumentException("办理流程内容长度限制1-5000个字符");
                }
                if (visaProcess.getImages() != null && visaProcess.getImages().size() > 1) {
                    throw new IllegalArgumentException("办理流程至多上传1张图片");
                }
            }
        }
    }

    private static void AssertMaterias(Map<String, List<VisaMaterial>> materias) {

        if (CollectionUtils.isEmpty(materias.get(CareerType.EMPLOYED.name()))) {
            throw new IllegalArgumentException("在职签证材料不能为空");
        } else {
            for (VisaMaterial visaMaterial : materias.get(CareerType.EMPLOYED.name())) {
                if (visaMaterial.getTitle() == null) {
                    throw new IllegalArgumentException("在职签证材料标题不能为空");
                } else if (CharCounter.getLength(visaMaterial.getTitle()) > 30 || CharCounter.getLength(visaMaterial.getTitle())<1) {
                    throw new IllegalArgumentException("在职签证材料标题长度限制1-30个字符");
                }
                if (visaMaterial.getContent() == null) {
                    throw new IllegalArgumentException("在职签证材料内容不能为空");
                } else if (CharCounter.getLength(visaMaterial.getContent()) > 5000 || CharCounter.getLength(visaMaterial.getContent())<1) {
                    throw new IllegalArgumentException("在职签证材料内容长度限制1-5000个字符");
                }
                if (visaMaterial.getImages() != null && visaMaterial.getImages().size() > 1) {
                    throw new IllegalArgumentException("在职签证材料至多上传1张图片");
                }
            }
        }

        if (CollectionUtils.isEmpty(materias.get(CareerType.FREE.name()))) {
            throw new IllegalArgumentException("自由职业者签证材料不能为空");
        } else {
            for (VisaMaterial visaMaterial : materias.get(CareerType.FREE.name())) {
                if (visaMaterial.getTitle() == null) {
                    throw new IllegalArgumentException("自由职业者签证材料标题不能为空");
                } else if (CharCounter.getLength(visaMaterial.getTitle()) > 30 || CharCounter.getLength(visaMaterial.getTitle())<1) {
                    throw new IllegalArgumentException("自由职业者签证材料标题长度限制1-30个字符");
                }
                if (visaMaterial.getContent() == null) {
                    throw new IllegalArgumentException("自由职业者签证材料内容不能为空");
                } else if (CharCounter.getLength(visaMaterial.getContent()) > 5000 || CharCounter.getLength(visaMaterial.getContent())<1) {
                    throw new IllegalArgumentException("自由职业者签证材料内容长度限制1-5000个字符");
                }
                if (visaMaterial.getImages() != null && visaMaterial.getImages().size() > 1) {
                    throw new IllegalArgumentException("自由职业者签证材料至多上传1张图片");
                }
            }
        }

        if (CollectionUtils.isEmpty(materias.get(CareerType.STUDENT.name()))) {
            throw new IllegalArgumentException("在校学生签证材料不能为空");
        } else {
            for (VisaMaterial visaMaterial : materias.get(CareerType.STUDENT.name())) {
                if (visaMaterial.getTitle() == null) {
                    throw new IllegalArgumentException("在校学生签证材料标题不能为空");
                } else if (CharCounter.getLength(visaMaterial.getTitle()) > 30 || CharCounter.getLength(visaMaterial.getTitle())<1) {
                    throw new IllegalArgumentException("在校学生签证材料标题长度限制1-30个字符");
                }
                if (visaMaterial.getContent() == null) {
                    throw new IllegalArgumentException("在校学生签证材料内容不能为空");
                } else if (CharCounter.getLength(visaMaterial.getContent()) > 5000 || CharCounter.getLength(visaMaterial.getContent())<1) {
                    throw new IllegalArgumentException("在校学生签证材料内容长度限制1-5000个字符");
                }
                if (visaMaterial.getImages() != null && visaMaterial.getImages().size() > 1) {
                    throw new IllegalArgumentException("在校学生签证材料至多上传1张图片");
                }
            }
        }

        if (CollectionUtils.isEmpty(materias.get(CareerType.RETIRE.name()))) {
            throw new IllegalArgumentException("退休签证材料不能为空");
        } else {
            for (VisaMaterial visaMaterial : materias.get(CareerType.RETIRE.name())) {
                if (visaMaterial.getTitle() == null) {
                    throw new IllegalArgumentException("退休签证材料标题不能为空");
                } else if (CharCounter.getLength(visaMaterial.getTitle()) > 30 || CharCounter.getLength(visaMaterial.getTitle())<1) {
                    throw new IllegalArgumentException("退休签证材料标题长度限制1-30个字符");
                }
                if (visaMaterial.getContent() == null) {
                    throw new IllegalArgumentException("退休签证材料内容不能为空");
                } else if (CharCounter.getLength(visaMaterial.getContent()) > 5000 || CharCounter.getLength(visaMaterial.getContent())<1) {
                    throw new IllegalArgumentException("退休签证材料内容长度限制1-5000个字符");
                }
                if (visaMaterial.getImages() != null && visaMaterial.getImages().size() > 1) {
                    throw new IllegalArgumentException("退休签证材料至多上传1张图片");
                }
            }
        }

        if (CollectionUtils.isEmpty(materias.get(CareerType.CHILD.name()))) {
            throw new IllegalArgumentException("学龄前儿童签证材料不能为空");
        } else {
            for (VisaMaterial visaMaterial : materias.get(CareerType.CHILD.name())) {
                if (visaMaterial.getTitle() == null) {
                    throw new IllegalArgumentException("学龄前儿童签证材料标题不能为空");
                } else if (CharCounter.getLength(visaMaterial.getTitle()) > 30 || CharCounter.getLength(visaMaterial.getTitle())<1) {
                    throw new IllegalArgumentException("学龄前儿童签证材料标题长度限制1-30个字符");
                }
                if (visaMaterial.getContent() == null) {
                    throw new IllegalArgumentException("学龄前儿童签证材料内容不能为空");
                } else if (CharCounter.getLength(visaMaterial.getContent()) > 5000 || CharCounter.getLength(visaMaterial.getContent())<1) {
                    throw new IllegalArgumentException("学龄前儿童签证材料内容长度限制1-5000个字符");
                }
                if (visaMaterial.getImages() != null && visaMaterial.getImages().size() > 1) {
                    throw new IllegalArgumentException("学龄前儿童签证材料至多上传1张图片");
                }
            }
        }
    }

    private static void AssertVisaDetailInfo(VisaDetailInfo detailInfo) {

        if (detailInfo == null) throw new IllegalArgumentException("详情信息不能为空");

        if (detailInfo.getIsInterview() == null) {
            throw new IllegalArgumentException("面试要求不能为空");
        }
        if (StringUtils.isBlank(detailInfo.getValidate())) {
            throw new IllegalArgumentException("签证有效期不能为空");
        } else if (CharCounter.getLength(detailInfo.getValidate()) > 50 || CharCounter.getLength(detailInfo.getValidate())<1) {
            throw new IllegalArgumentException("签证有效期长度限制1-50个字符");
        }
        if (StringUtils.isBlank(detailInfo.getStayTime())) {
            throw new IllegalArgumentException("可停留日期不能为空");
        } else if (CharCounter.getLength(detailInfo.getStayTime()) > 50 || CharCounter.getLength(detailInfo.getStayTime())<1) {
            throw new IllegalArgumentException("可停留日期长度限制1-50个字符");
        }
        if (StringUtils.isBlank(detailInfo.getTimes())) {
            throw new IllegalArgumentException("可入境次数不能为空");
        } else if (CharCounter.getLength(detailInfo.getTimes()) > 10 || CharCounter.getLength(detailInfo.getTimes())<1) {
            throw new IllegalArgumentException("可入境次数长度限制1-10个字符");
        }
        if (StringUtils.isBlank(detailInfo.getScope())) {
            throw new IllegalArgumentException("受理范围不能为空");
        } else if (CharCounter.getLength(detailInfo.getScope()) > 500 || CharCounter.getLength(detailInfo.getScope())<1) {
            throw new IllegalArgumentException("受理范围长度限制1-500个字符");
        }
        if (CollectionUtils.isEmpty(detailInfo.getImages()) || detailInfo.getImages().size() <1) {
            throw new IllegalArgumentException("必须至少上传1张图片");
        }

    }

    private static void AssertVisaAdditionalInfo(Map<AdditionalRule, String> additionalInfos) {

        if (additionalInfos == null) throw new IllegalArgumentException("输入附加信息不能为空");

        if (additionalInfos.get(AdditionalRule.BOOKING) == null) {
            throw new IllegalArgumentException("预订须知不能为空");
        } else if (CharCounter.getLength(additionalInfos.get(AdditionalRule.BOOKING)) > 5000 || CharCounter.getLength(additionalInfos.get(AdditionalRule.BOOKING))<1) {
            throw new IllegalArgumentException("预订须知长度限制1-5000个字符");
        }

        if (additionalInfos.get(AdditionalRule.FEE_DETAIL) == null) {
            throw new IllegalArgumentException("费用说明不能为空");
        } else if (CharCounter.getLength(additionalInfos.get(AdditionalRule.FEE_DETAIL)) > 5000 || CharCounter.getLength(additionalInfos.get(AdditionalRule.FEE_DETAIL))<1) {
            throw new IllegalArgumentException("费用说明长度限制1-5000个字符");
        }

        if (additionalInfos.get(AdditionalRule.REFUND_POLICY) == null) {
            throw new IllegalArgumentException("退改政策不能为空");
        } else if (CharCounter.getLength(additionalInfos.get(AdditionalRule.REFUND_POLICY)) > 5000 || CharCounter.getLength(additionalInfos.get(AdditionalRule.REFUND_POLICY))<1) {
            throw new IllegalArgumentException("退改政策长度限制1-5000个字符");
        }

    }

    private static void AssertUnVisaAdditionalInfo(Map<AdditionalRule, String> additionalInfos) {

        if (additionalInfos == null) throw new IllegalArgumentException("输入附加信息不能为空");

        if (additionalInfos.get(AdditionalRule.FEATURES) == null) {
            throw new IllegalArgumentException("产品介绍不能为空");
        } else if (CharCounter.getLength(additionalInfos.get(AdditionalRule.FEATURES)) > 5000 || CharCounter.getLength(additionalInfos.get(AdditionalRule.FEATURES))<1) {
            throw new IllegalArgumentException("产品介绍长度限制1-5000个字符");
        }

        if (additionalInfos.get(AdditionalRule.INTRODUCTIONS) == null) {
            throw new IllegalArgumentException("使用说明不能为空");
        } else if (CharCounter.getLength(additionalInfos.get(AdditionalRule.INTRODUCTIONS)) > 5000 || CharCounter.getLength(additionalInfos.get(AdditionalRule.INTRODUCTIONS))<1) {
            throw new IllegalArgumentException("使用说明长度限制1-5000个字符");
        }

        if (additionalInfos.get(AdditionalRule.BOOKING) == null) {
            throw new IllegalArgumentException("预订须知不能为空");
        } else if (CharCounter.getLength(additionalInfos.get(AdditionalRule.BOOKING)) > 5000 || CharCounter.getLength(additionalInfos.get(AdditionalRule.BOOKING))<1) {
            throw new IllegalArgumentException("预订须知长度限制1-5000个字符");
        }

        if (additionalInfos.get(AdditionalRule.FEE_DETAIL) == null) {
            throw new IllegalArgumentException("费用说明不能为空");
        } else if (CharCounter.getLength(additionalInfos.get(AdditionalRule.FEE_DETAIL)) > 5000 || CharCounter.getLength(additionalInfos.get(AdditionalRule.FEE_DETAIL))<1) {
            throw new IllegalArgumentException("费用说明长度限制1-5000个字符");
        }

        if (additionalInfos.get(AdditionalRule.REFUND_POLICY) == null) {
            throw new IllegalArgumentException("退改政策不能为空");
        } else if (CharCounter.getLength(additionalInfos.get(AdditionalRule.REFUND_POLICY)) > 5000 || CharCounter.getLength(additionalInfos.get(AdditionalRule.REFUND_POLICY))<1) {
            throw new IllegalArgumentException("退改政策长度限制1-5000个字符");
        }

    }

    private static void AssertPriceInfo(List<PriceInfo> priceInfos) {

        if (CollectionUtils.isEmpty(priceInfos)) throw new IllegalArgumentException("至少需要配置一种成本价格");

        for (PriceInfo priceInfo : priceInfos) {
            if (StringUtils.isBlank(priceInfo.getName())) {
                throw new IllegalArgumentException("价格类型名称不能为空");
            } else if (CharCounter.getLength(priceInfo.getName()) > 100 || CharCounter.getLength(priceInfo.getName())<1) {
                throw new IllegalArgumentException("价格类型名称长度限制1-100个字符");
            }

            if (priceInfo.getAdultCost() == null) {
                throw new IllegalArgumentException("成人底价不能为空");
            } else if (!priceInfo.getAdultCost().toString().matches(priceReg)) {
                throw new IllegalArgumentException("成人底价取值范围为:0-99999，可输入两位小数");
            }

            if (priceInfo.getHasChildPrice() != null && priceInfo.getHasChildPrice()) {
                if (priceInfo.getChildCost() == null) {
                    throw new IllegalArgumentException("勾选儿童时儿童底价不能为空");
                } else if (!priceInfo.getChildCost().toString().matches(priceReg)) {
                    throw new IllegalArgumentException("儿童底价取值范围为:0-99999，可输入两位小数");
                }
            }

            if (priceInfo.getAdultPrice() == null) {
                throw new IllegalArgumentException("成人售价不能为空");
            } else if (!priceInfo.getAdultPrice().toString().matches(priceReg)) {
                throw new IllegalArgumentException("成人售价取值范围为:0-99999，可输入两位小数");
            }

            if (priceInfo.getHasChildPrice() != null && priceInfo.getHasChildPrice()) {
                if (priceInfo.getChildPrice() == null) {
                    throw new IllegalArgumentException("勾选儿童时儿童售价不能为空");
                } else if (!priceInfo.getChildPrice().toString().matches(priceReg)) {
                    throw new IllegalArgumentException("儿童售价取值范围为:0-99999，可输入两位小数");
                }
            }
        }

    }

    private static void AssertUnVisaDetailInfo(UnVisaDetailInfo detailInfo) {

        if (detailInfo == null) throw new IllegalArgumentException("详情信息不能为空");

        if (StringUtils.isBlank(detailInfo.getLanguageType())) {
            throw new IllegalArgumentException("服务语言不能为空");
        }
        if (StringUtils.isBlank(detailInfo.getServiceTime())) {
            throw new IllegalArgumentException("服务时间不能为空");
        } else if (CharCounter.getLength(detailInfo.getServiceTime()) > 50 || CharCounter.getLength(detailInfo.getServiceTime()) < 1) {
            throw new IllegalArgumentException("服务时间长度限制1-50个字符");
        }
        if (CollectionUtils.isEmpty(detailInfo.getImages()) || detailInfo.getImages().size() <1 || detailInfo.getImages().size() > 4) {
            throw new IllegalArgumentException("必须上传1-4张图片");
        }

    }

    private static void AssertBasicInfo(BasicInfo basicInfo) {

        if (basicInfo == null) throw new IllegalArgumentException("基本信息不能为空");

        if (StringUtils.isBlank(basicInfo.getPname())) {
            throw new IllegalArgumentException("基本信息 标题不能为空");
        } else if (CharCounter.getLength(basicInfo.getPname()) > 100 || CharCounter.getLength(basicInfo.getPname())<1) {
            throw new IllegalArgumentException("基本信息 标题长度限制1-100个字符");
        }
        if (CollectionUtils.isEmpty(basicInfo.getToCity())) {
            throw new IllegalArgumentException("目的地必填");
        }
        if (basicInfo.getType() == null) {
            throw new IllegalArgumentException("产品种类必填");
        }

    }

}
