package com.ztravel.product.back.pieces.utils;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.ztravel.common.enums.AdditionalRule;
import com.ztravel.common.enums.CareerType;
import com.ztravel.common.enums.Nature;
import com.ztravel.common.enums.PieceType;
import com.ztravel.product.back.freetravel.utils.CharCounter;
import com.ztravel.product.back.pieces.vo.PieceBasicInfoVo;
import com.ztravel.product.back.pieces.vo.PiecePriceInfoVo;
import com.ztravel.product.back.pieces.vo.UnVisaAdditionalInfoVo;
import com.ztravel.product.back.pieces.vo.UnVisaDetailInfoVo;
import com.ztravel.product.back.pieces.vo.VisaAdditionalInfoVo;
import com.ztravel.product.back.pieces.vo.VisaDetailInfoVo;
import com.ztravel.product.po.pieces.common.PriceInfo;
import com.ztravel.product.po.pieces.visa.VisaMaterial;
import com.ztravel.product.po.pieces.visa.VisaProcess;

public class PiecesValidator {

    //private static final String serviceTimeReg = "^(([0-9]|[1-9][0-9])(\\.[0-9]{0,1})?|100(\\.0)?)$";
    private static final String priceReg = "^(([1-9][0-9]{0,4}|0)(\\.[0-9]{0,2})?)$";

	public static void AssertBasicInfo(PieceBasicInfoVo vo) throws IllegalArgumentException {
		if (StringUtils.isBlank(vo.getPname())) {
			throw new IllegalArgumentException("标题不能为空");
		} else if (CharCounter.getLength(vo.getPname()) > 100 || CharCounter.getLength(vo.getPname()) < 1) {
			throw new IllegalArgumentException("标题长度限制1-100个字符");
		}
		if (CollectionUtils.isEmpty(vo.getToCity())) {
			throw new IllegalArgumentException("目的地必填");
		}
		if (StringUtils.isBlank(vo.getType())) {
			throw new IllegalArgumentException("产品种类必填");
		}
        if (StringUtils.isNotBlank(vo.getType()) && StringUtils.isNotBlank(vo.getNature())) {
		    if (PieceType.VISA.name().equals(vo.getType()) && !Nature.VISA.name().equals(vo.getNature())
		            || !PieceType.VISA.name().equals(vo.getType()) && Nature.VISA.name().equals(vo.getNature())) {
		        throw new IllegalArgumentException("产品种类录入后不能更改");
		    }
		}
	}

	public static void AssertBasicInfoWithoutNext(PieceBasicInfoVo vo) throws IllegalArgumentException {
        if (StringUtils.isBlank(vo.getPname())) {
            throw new IllegalArgumentException("标题不能为空");
        } else if (CharCounter.getLength(vo.getPname()) > 100 || CharCounter.getLength(vo.getPname()) < 1) {
            throw new IllegalArgumentException("标题长度限制1-100个字符");
        }
        if (StringUtils.isNotBlank(vo.getType()) && StringUtils.isNotBlank(vo.getNature())) {
            if (PieceType.VISA.name().equals(vo.getType()) && !Nature.VISA.name().equals(vo.getNature())
                    || !PieceType.VISA.name().equals(vo.getType()) && Nature.VISA.name().equals(vo.getNature())) {
                throw new IllegalArgumentException("产品种类录入后不能更改");
            }
        }
    }

	public static void AssertUnVisaDetailInfo(UnVisaDetailInfoVo vo) throws IllegalArgumentException {
        if (StringUtils.isBlank(vo.getLanguageType())) {
            throw new IllegalArgumentException("服务语言不能为空");
        }
        if (StringUtils.isBlank(vo.getServiceTime())) {
            throw new IllegalArgumentException("服务时间不能为空");
        } else if (CharCounter.getLength(vo.getServiceTime()) > 50 || CharCounter.getLength(vo.getServiceTime()) < 1) {
            throw new IllegalArgumentException("服务时间长度限制1-50个字符");
        }
        if (CollectionUtils.isEmpty(vo.getImages()) && (vo.getImages().size() <1 || vo.getImages().size() > 4)) {
            throw new IllegalArgumentException("必须上传1-4张图片");
        }
    }

    public static void AssertUnVisaDetailInfoWithoutNext(UnVisaDetailInfoVo vo) throws IllegalArgumentException {
        if (StringUtils.isNotBlank(vo.getServiceTime()) && (CharCounter.getLength(vo.getServiceTime()) > 50 || CharCounter.getLength(vo.getServiceTime()) < 1)) {
            throw new IllegalArgumentException("服务时间长度限制1-50个字符");
        }
        if (!CollectionUtils.isEmpty(vo.getImages()) && (vo.getImages().size() <1 || vo.getImages().size() > 4)) {
            throw new IllegalArgumentException("必须上传1-4张图片");
        }
    }


	public static void AssertVisaDetailInfo(VisaDetailInfoVo vo) throws IllegalArgumentException {
	    if (vo.getIsInterview() == null) {
            throw new IllegalArgumentException("面试要求不能为空");
        }
        if (StringUtils.isBlank(vo.getValidate())) {
            throw new IllegalArgumentException("签证有效期不能为空");
        } else if (CharCounter.getLength(vo.getValidate()) > 50 || CharCounter.getLength(vo.getValidate()) < 1) {
        }
        if (StringUtils.isBlank(vo.getStayTime())) {
            throw new IllegalArgumentException("可停留日期不能为空");
        } else if (CharCounter.getLength(vo.getStayTime()) > 50 || CharCounter.getLength(vo.getStayTime()) < 1) {
            throw new IllegalArgumentException("可停留日期长度限制1-50个字符");
        }
        if (StringUtils.isBlank(vo.getTimes())) {
            throw new IllegalArgumentException("可入境次数不能为空");
        } else if (CharCounter.getLength(vo.getTimes()) > 10 || CharCounter.getLength(vo.getTimes()) < 1) {
            throw new IllegalArgumentException("可入境次数长度限制1-10个字符");
        }
        if (StringUtils.isBlank(vo.getScope())) {
            throw new IllegalArgumentException("受理范围不能为空");
        } else if (CharCounter.getLength(vo.getScope()) > 500 || CharCounter.getLength(vo.getScope()) < 1) {
            throw new IllegalArgumentException("受理范围长度限制1-500个字符");
        }
        if (CollectionUtils.isEmpty(vo.getImages()) || vo.getImages().size() <1) {
            throw new IllegalArgumentException("必须至少上传1张图片");
        }
    }

    public static void AssertVisaDetailInfoWithoutNext(VisaDetailInfoVo vo) throws IllegalArgumentException {
        if (StringUtils.isNotBlank(vo.getValidate()) && (CharCounter.getLength(vo.getValidate()) > 50 || CharCounter.getLength(vo.getValidate()) < 1)) {
            throw new IllegalArgumentException("签证有效期长度限制1-50个字符");
        }
        if (StringUtils.isNotBlank(vo.getStayTime()) && (CharCounter.getLength(vo.getStayTime()) > 50 || CharCounter.getLength(vo.getStayTime()) < 1)) {
            throw new IllegalArgumentException("可停留日期长度限制1-50个字符");
        }
        if (StringUtils.isNotBlank(vo.getTimes()) && (CharCounter.getLength(vo.getTimes()) > 10 || CharCounter.getLength(vo.getTimes()) < 1)) {
            throw new IllegalArgumentException("可入境次数长度限制1-10个字符");
        }
        if (StringUtils.isNotBlank(vo.getScope()) && (CharCounter.getLength(vo.getScope()) > 500 || CharCounter.getLength(vo.getScope()) < 1)) {
            throw new IllegalArgumentException("受理范围长度限制1-500个字符");
        }
    }

    public static void AssertPriceInfo(PiecePriceInfoVo vo) throws IllegalArgumentException {

        if (CollectionUtils.isEmpty(vo.getPriceInfos())) {
            throw new IllegalArgumentException("至少需要配置一种成本价格");
        }
        for (PriceInfo priceInfo : vo.getPriceInfos()) {
            if (StringUtils.isBlank(priceInfo.getName())) {
                throw new IllegalArgumentException("价格类型名称不能为空");
            } else if (CharCounter.getLength(priceInfo.getName()) > 100 || CharCounter.getLength(priceInfo.getName()) < 1) {
                throw new IllegalArgumentException("价格类型名称长度限制1-100个字符");
            }

            if (vo.getPriceType() != null && "cost".equals(vo.getPriceType())) {
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
            }

            if (vo.getPriceType() != null && "sale".equals(vo.getPriceType())) {
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
    }

    public static void AssertPriceInfoWithoutNext(PiecePriceInfoVo vo) throws IllegalArgumentException {

        if (CollectionUtils.isEmpty(vo.getPriceInfos())) {
            return ;
        }
        for (PriceInfo priceInfo : vo.getPriceInfos()) {
            if (StringUtils.isNotBlank(priceInfo.getName()) && (CharCounter.getLength(priceInfo.getName()) > 100 || CharCounter.getLength(priceInfo.getName()) < 1)) {
                throw new IllegalArgumentException("价格类型名称长度限制1-100个字符");
            }

            if (vo.getPriceType() != null && "cost".equals(vo.getPriceType())) {
                if (priceInfo.getAdultCost() != null && !priceInfo.getAdultCost().toString().matches(priceReg)) {
                    throw new IllegalArgumentException("成人底价取值范围为:0-99999，可输入两位小数");
                }

                if (priceInfo.getHasChildPrice() != null && priceInfo.getHasChildPrice()) {
                    if (priceInfo.getChildCost() != null && !priceInfo.getChildCost().toString().matches(priceReg)) {
                        throw new IllegalArgumentException("儿童底价取值范围为:0-99999，可输入两位小数");
                    }
                }
            }

            if (vo.getPriceType() != null && "sale".equals(vo.getPriceType())) {
                if (priceInfo.getAdultPrice() != null && !priceInfo.getAdultPrice().toString().matches(priceReg)) {
                    throw new IllegalArgumentException("成人售价取值范围为:0-99999，可输入两位小数");
                }

                if (priceInfo.getHasChildPrice() != null && priceInfo.getHasChildPrice()) {
                    if (priceInfo.getChildPrice() != null && !priceInfo.getChildPrice().toString().matches(priceReg)) {
                        throw new IllegalArgumentException("儿童售价取值范围为:0-99999，可输入两位小数");
                    }
                }
            }
        }
    }

    public static void AssertUnVisaAdditionalInfo(UnVisaAdditionalInfoVo vo) throws IllegalArgumentException {
        if (vo.getAdditionalInfos() == null) {
            throw new IllegalArgumentException("输入附加信息不能为空");
        }

        Map<String, String> additionalInfos = vo.getAdditionalInfos();

        if (additionalInfos.get(AdditionalRule.FEATURES.name()) == null) {
            throw new IllegalArgumentException("产品介绍不能为空");
        } else if (CharCounter.getLength(additionalInfos.get(AdditionalRule.FEATURES.name())) > 5000 || CharCounter.getLength(additionalInfos.get(AdditionalRule.FEATURES.name())) < 1) {
            throw new IllegalArgumentException("产品介绍长度限制1-5000个字符");
        }

        if (additionalInfos.get(AdditionalRule.INTRODUCTIONS.name()) == null) {
            throw new IllegalArgumentException("使用说明不能为空");
        } else if (CharCounter.getLength(additionalInfos.get(AdditionalRule.INTRODUCTIONS.name())) > 5000 || CharCounter.getLength(additionalInfos.get(AdditionalRule.INTRODUCTIONS.name())) < 1) {
            throw new IllegalArgumentException("使用说明长度限制1-5000个字符");
        }

        if (additionalInfos.get(AdditionalRule.BOOKING.name()) == null) {
            throw new IllegalArgumentException("预订须知不能为空");
        } else if (CharCounter.getLength(additionalInfos.get(AdditionalRule.BOOKING.name())) > 5000 || CharCounter.getLength(additionalInfos.get(AdditionalRule.BOOKING.name())) < 1) {
            throw new IllegalArgumentException("预订须知长度限制1-5000个字符");
        }

        if (additionalInfos.get(AdditionalRule.FEE_DETAIL.name()) == null) {
            throw new IllegalArgumentException("费用说明不能为空");
        } else if (CharCounter.getLength(additionalInfos.get(AdditionalRule.FEE_DETAIL.name())) > 5000 || CharCounter.getLength(additionalInfos.get(AdditionalRule.FEE_DETAIL.name())) < 1) {
            throw new IllegalArgumentException("费用说明长度限制1-5000个字符");
        }

        if (additionalInfos.get(AdditionalRule.REFUND_POLICY.name()) == null) {
            throw new IllegalArgumentException("退改政策不能为空");
        } else if (CharCounter.getLength(additionalInfos.get(AdditionalRule.REFUND_POLICY.name())) > 5000 || CharCounter.getLength(additionalInfos.get(AdditionalRule.REFUND_POLICY.name())) < 1) {
            throw new IllegalArgumentException("退改政策长度限制1-5000个字符");
        }

    }


    public static void AssertUnVisaAdditionalInfoWithoutNext(UnVisaAdditionalInfoVo vo) throws IllegalArgumentException {
        if (vo.getAdditionalInfos() == null) {
            return ;
        }

        Map<String, String> additionalInfos = vo.getAdditionalInfos();

        if (StringUtils.isNotBlank(additionalInfos.get(AdditionalRule.FEATURES.name())) && (CharCounter.getLength(additionalInfos.get(AdditionalRule.FEATURES.name())) > 5000 || CharCounter.getLength(additionalInfos.get(AdditionalRule.FEATURES.name())) < 1)) {
            throw new IllegalArgumentException("产品介绍长度限制1-5000个字符");
        }

        if (StringUtils.isNotBlank(additionalInfos.get(AdditionalRule.INTRODUCTIONS.name())) && (CharCounter.getLength(additionalInfos.get(AdditionalRule.INTRODUCTIONS.name())) > 5000 || CharCounter.getLength(additionalInfos.get(AdditionalRule.INTRODUCTIONS.name())) < 1)) {
            throw new IllegalArgumentException("使用说明长度限制1-5000个字符");
        }

        if (StringUtils.isNotBlank(additionalInfos.get(AdditionalRule.BOOKING.name())) && (CharCounter.getLength(additionalInfos.get(AdditionalRule.BOOKING.name())) > 5000 || CharCounter.getLength(additionalInfos.get(AdditionalRule.BOOKING.name())) < 1)) {
            throw new IllegalArgumentException("预订须知长度限制1-5000个字符");
        }

        if (StringUtils.isNotBlank(additionalInfos.get(AdditionalRule.FEE_DETAIL.name())) && (CharCounter.getLength(additionalInfos.get(AdditionalRule.FEE_DETAIL.name())) > 5000 || CharCounter.getLength(additionalInfos.get(AdditionalRule.FEE_DETAIL.name())) < 1)) {
            throw new IllegalArgumentException("费用说明长度限制1-5000个字符");
        }

        if (StringUtils.isNotBlank(additionalInfos.get(AdditionalRule.REFUND_POLICY.name())) && (CharCounter.getLength(additionalInfos.get(AdditionalRule.REFUND_POLICY.name())) > 5000 || CharCounter.getLength(additionalInfos.get(AdditionalRule.REFUND_POLICY.name())) < 1)) {
            throw new IllegalArgumentException("退改政策长度限制1-5000个字符");
        }

    }

    public static void AssertVisaAdditionalInfo(VisaAdditionalInfoVo vo) throws IllegalArgumentException {

        if (CollectionUtils.isEmpty(vo.getMaterias())) {
            throw new IllegalArgumentException("签证材料不能为空");
        }

        Map<String, List<VisaMaterial>> visaMaterials = vo.getMaterias();

        if (CollectionUtils.isEmpty(visaMaterials.get(CareerType.EMPLOYED.name()))) {
            throw new IllegalArgumentException("在职签证材料不能为空");
        } else {
            for (VisaMaterial visaMaterial : visaMaterials.get(CareerType.EMPLOYED.name())) {
                if (visaMaterial.getTitle() == null) {
                    throw new IllegalArgumentException("在职签证材料标题不能为空");
                } else if (CharCounter.getLength(visaMaterial.getTitle()) > 30 || CharCounter.getLength(visaMaterial.getTitle()) < 1) {
                    throw new IllegalArgumentException("在职签证材料标题长度限制1-30个字符");
                }
                if (visaMaterial.getContent() == null) {
                    throw new IllegalArgumentException("在职签证材料内容不能为空");
                } else if (CharCounter.getLength(visaMaterial.getContent()) > 5000 || CharCounter.getLength(visaMaterial.getContent()) < 1) {
                    throw new IllegalArgumentException("在职签证材料内容长度限制1-5000个字符");
                }
                if (visaMaterial.getImages() != null && visaMaterial.getImages().size() > 1) {
                    throw new IllegalArgumentException("在职签证材料至多上传1张图片");
                }
            }
        }

        if (CollectionUtils.isEmpty(visaMaterials.get(CareerType.FREE.name()))) {
            throw new IllegalArgumentException("自由职业者签证材料不能为空");
        } else {
            for (VisaMaterial visaMaterial : visaMaterials.get(CareerType.FREE.name())) {
                if (visaMaterial.getTitle() == null) {
                    throw new IllegalArgumentException("自由职业者签证材料标题不能为空");
                } else if (CharCounter.getLength(visaMaterial.getTitle()) > 30 || CharCounter.getLength(visaMaterial.getTitle()) < 1) {
                    throw new IllegalArgumentException("自由职业者签证材料标题长度限制1-30个字符");
                }
                if (visaMaterial.getContent() == null) {
                    throw new IllegalArgumentException("自由职业者签证材料内容不能为空");
                } else if (CharCounter.getLength(visaMaterial.getContent()) > 5000 || CharCounter.getLength(visaMaterial.getContent()) < 1) {
                    throw new IllegalArgumentException("自由职业者签证材料内容长度限制1-5000个字符");
                }
                if (visaMaterial.getImages() != null && visaMaterial.getImages().size() > 1) {
                    throw new IllegalArgumentException("自由职业者签证材料至多上传1张图片");
                }
            }
        }

        if (CollectionUtils.isEmpty(visaMaterials.get(CareerType.STUDENT.name()))) {
            throw new IllegalArgumentException("在校学生签证材料不能为空");
        } else {
            for (VisaMaterial visaMaterial : visaMaterials.get(CareerType.STUDENT.name())) {
                if (visaMaterial.getTitle() == null) {
                    throw new IllegalArgumentException("在校学生签证材料标题不能为空");
                } else if (CharCounter.getLength(visaMaterial.getTitle()) > 30 || CharCounter.getLength(visaMaterial.getTitle()) < 1) {
                    throw new IllegalArgumentException("在校学生签证材料标题长度限制1-30个字符");
                }
                if (visaMaterial.getContent() == null) {
                    throw new IllegalArgumentException("在校学生签证材料内容不能为空");
                } else if (CharCounter.getLength(visaMaterial.getContent()) > 5000 || CharCounter.getLength(visaMaterial.getContent()) < 1) {
                    throw new IllegalArgumentException("在校学生签证材料内容长度限制1-5000个字符");
                }
                if (visaMaterial.getImages() != null && visaMaterial.getImages().size() > 1) {
                    throw new IllegalArgumentException("在校学生签证材料至多上传1张图片");
                }
            }
        }

        if (CollectionUtils.isEmpty(visaMaterials.get(CareerType.RETIRE.name()))) {
            throw new IllegalArgumentException("退休签证材料不能为空");
        } else {
            for (VisaMaterial visaMaterial : visaMaterials.get(CareerType.RETIRE.name())) {
                if (visaMaterial.getTitle() == null) {
                    throw new IllegalArgumentException("退休签证材料标题不能为空");
                } else if (CharCounter.getLength(visaMaterial.getTitle()) > 30 || CharCounter.getLength(visaMaterial.getTitle()) < 1) {
                    throw new IllegalArgumentException("退休签证材料标题长度限制1-30个字符");
                }
                if (visaMaterial.getContent() == null) {
                    throw new IllegalArgumentException("退休签证材料内容不能为空");
                } else if (CharCounter.getLength(visaMaterial.getContent()) > 5000 || CharCounter.getLength(visaMaterial.getContent()) < 1) {
                    throw new IllegalArgumentException("退休签证材料内容长度限制1-5000个字符");
                }
                if (visaMaterial.getImages() != null && visaMaterial.getImages().size() > 1) {
                    throw new IllegalArgumentException("退休签证材料至多上传1张图片");
                }
            }
        }

        if (CollectionUtils.isEmpty(visaMaterials.get(CareerType.CHILD.name()))) {
            throw new IllegalArgumentException("学龄前儿童签证材料不能为空");
        } else {
            for (VisaMaterial visaMaterial : visaMaterials.get(CareerType.CHILD.name())) {
                if (visaMaterial.getTitle() == null) {
                    throw new IllegalArgumentException("学龄前儿童签证材料标题不能为空");
                } else if (CharCounter.getLength(visaMaterial.getTitle()) > 30 || CharCounter.getLength(visaMaterial.getTitle()) < 1) {
                    throw new IllegalArgumentException("学龄前儿童签证材料标题长度限制1-30个字符");
                }
                if (visaMaterial.getContent() == null) {
                    throw new IllegalArgumentException("学龄前儿童签证材料内容不能为空");
                } else if (CharCounter.getLength(visaMaterial.getContent()) > 5000 || CharCounter.getLength(visaMaterial.getContent()) < 1) {
                    throw new IllegalArgumentException("学龄前儿童签证材料内容长度限制1-5000个字符");
                }
                if (visaMaterial.getImages() != null && visaMaterial.getImages().size() > 1) {
                    throw new IllegalArgumentException("学龄前儿童签证材料至多上传1张图片");
                }
            }
        }

        List<VisaProcess> visaProcesses = vo.getProcesses();

        if (CollectionUtils.isEmpty(visaProcesses)) {
            throw new IllegalArgumentException("办理流程不能为空");
        } else {
            for (VisaProcess visaProcess : visaProcesses) {
                if (visaProcess.getTitle() == null) {
                    throw new IllegalArgumentException("办理流程标题不能为空");
                } else if (CharCounter.getLength(visaProcess.getTitle()) > 30 || CharCounter.getLength(visaProcess.getTitle()) < 1) {
                    throw new IllegalArgumentException("办理流程标题长度限制1-30个字符");
                }
                if (visaProcess.getContent() == null) {
                    throw new IllegalArgumentException("办理流程内容不能为空");
                } else if (CharCounter.getLength(visaProcess.getContent()) > 5000 || CharCounter.getLength(visaProcess.getContent()) < 1) {
                    throw new IllegalArgumentException("办理流程内容长度限制1-5000个字符");
                }
                if (visaProcess.getImages() != null && visaProcess.getImages().size() > 1) {
                    throw new IllegalArgumentException("办理流程至多上传1张图片");
                }
            }
        }

        if (CollectionUtils.isEmpty(vo.getAdditionalInfos())) {
            throw new IllegalArgumentException("输入附加信息不能为空");
        }

        Map<String, String> additionalInfos = vo.getAdditionalInfos();

        if (additionalInfos.get(AdditionalRule.BOOKING.name()) == null) {
            throw new IllegalArgumentException("预订须知不能为空");
        } else if (CharCounter.getLength(additionalInfos.get(AdditionalRule.BOOKING.name())) > 5000 || CharCounter.getLength(additionalInfos.get(AdditionalRule.BOOKING.name())) < 1) {
            throw new IllegalArgumentException("预订须知长度限制1-5000个字符");
        }

        if (additionalInfos.get(AdditionalRule.FEE_DETAIL.name()) == null) {
            throw new IllegalArgumentException("费用说明不能为空");
        } else if (CharCounter.getLength(additionalInfos.get(AdditionalRule.FEE_DETAIL.name())) > 5000 || CharCounter.getLength(additionalInfos.get(AdditionalRule.FEE_DETAIL.name())) < 1) {
            throw new IllegalArgumentException("费用说明长度限制1-5000个字符");
        }

        if (additionalInfos.get(AdditionalRule.REFUND_POLICY.name()) == null) {
            throw new IllegalArgumentException("退改政策不能为空");
        } else if (CharCounter.getLength(additionalInfos.get(AdditionalRule.REFUND_POLICY.name())) > 5000 || CharCounter.getLength(additionalInfos.get(AdditionalRule.REFUND_POLICY.name())) < 1) {
            throw new IllegalArgumentException("退改政策长度限制1-5000个字符");
        }

    }

    public static void AssertVisaAdditionalInfoWithoutNext(VisaAdditionalInfoVo vo) throws IllegalArgumentException {

        if (!CollectionUtils.isEmpty(vo.getMaterias())) {
            Map<String, List<VisaMaterial>> visaMaterials = vo.getMaterias();

            if (!CollectionUtils.isEmpty(visaMaterials.get(CareerType.EMPLOYED.name()))) {
                for (VisaMaterial visaMaterial : visaMaterials.get(CareerType.EMPLOYED.name())) {
                    if (StringUtils.isNotBlank(visaMaterial.getTitle()) && (CharCounter.getLength(visaMaterial.getTitle()) > 30 || CharCounter.getLength(visaMaterial.getTitle()) < 1)) {
                        throw new IllegalArgumentException("在职签证材料标题长度限制1-30个字符");
                    }
                    if (StringUtils.isNotBlank(visaMaterial.getContent()) && (CharCounter.getLength(visaMaterial.getContent()) > 5000 || CharCounter.getLength(visaMaterial.getContent()) < 1)) {
                        throw new IllegalArgumentException("在职签证材料内容长度限制1-5000个字符");
                    }
                    if (visaMaterial.getImages() != null && visaMaterial.getImages().size() > 1) {
                        throw new IllegalArgumentException("在职签证材料至多上传1张图片");
                    }
                }
            }

            if (!CollectionUtils.isEmpty(visaMaterials.get(CareerType.FREE.name()))) {
                for (VisaMaterial visaMaterial : visaMaterials.get(CareerType.FREE.name())) {
                    if (StringUtils.isNotBlank(visaMaterial.getTitle()) && (CharCounter.getLength(visaMaterial.getTitle()) > 30 || CharCounter.getLength(visaMaterial.getTitle()) < 1)) {
                        throw new IllegalArgumentException("自由职业者签证材料标题长度限制1-30个字符");
                    }
                    if (StringUtils.isNotBlank(visaMaterial.getContent()) && (CharCounter.getLength(visaMaterial.getContent()) > 5000 || CharCounter.getLength(visaMaterial.getContent()) < 1)) {
                        throw new IllegalArgumentException("自由职业者签证材料内容长度限制1-5000个字符");
                    }
                    if (visaMaterial.getImages() != null && visaMaterial.getImages().size() > 1) {
                        throw new IllegalArgumentException("自由职业者签证材料至多上传1张图片");
                    }
                }
            }

            if (!CollectionUtils.isEmpty(visaMaterials.get(CareerType.STUDENT.name()))) {
                for (VisaMaterial visaMaterial : visaMaterials.get(CareerType.STUDENT.name())) {
                    if (StringUtils.isNotBlank(visaMaterial.getTitle()) && (CharCounter.getLength(visaMaterial.getTitle()) > 30 || CharCounter.getLength(visaMaterial.getTitle()) < 1)) {
                        throw new IllegalArgumentException("在校学生签证材料标题长度限制1-30个字符");
                    }
                    if (StringUtils.isNotBlank(visaMaterial.getContent()) && (CharCounter.getLength(visaMaterial.getContent()) > 5000 || CharCounter.getLength(visaMaterial.getContent()) < 1)) {
                        throw new IllegalArgumentException("在校学生签证材料内容长度限制1-5000个字符");
                    }
                    if (visaMaterial.getImages() != null && visaMaterial.getImages().size() > 1) {
                        throw new IllegalArgumentException("在校学生签证材料至多上传1张图片");
                    }
                }
            }

            if (!CollectionUtils.isEmpty(visaMaterials.get(CareerType.RETIRE.name()))) {
                for (VisaMaterial visaMaterial : visaMaterials.get(CareerType.RETIRE.name())) {
                    if (StringUtils.isNotBlank(visaMaterial.getTitle()) && (CharCounter.getLength(visaMaterial.getTitle()) > 30 || CharCounter.getLength(visaMaterial.getTitle()) < 1)) {
                        throw new IllegalArgumentException("退休签证材料标题长度限制1-30个字符");
                    }
                    if (StringUtils.isNotBlank(visaMaterial.getContent()) && (CharCounter.getLength(visaMaterial.getContent()) > 5000 || CharCounter.getLength(visaMaterial.getContent()) < 1)) {
                        throw new IllegalArgumentException("退休签证材料内容长度限制1-5000个字符");
                    }
                    if (visaMaterial.getImages() != null && visaMaterial.getImages().size() > 1) {
                        throw new IllegalArgumentException("退休签证材料至多上传1张图片");
                    }
                }
            }

            if (!CollectionUtils.isEmpty(visaMaterials.get(CareerType.CHILD.name()))) {
                for (VisaMaterial visaMaterial : visaMaterials.get(CareerType.CHILD.name())) {
                    if (StringUtils.isNotBlank(visaMaterial.getTitle()) && (CharCounter.getLength(visaMaterial.getTitle()) > 30 || CharCounter.getLength(visaMaterial.getTitle()) < 1)) {
                        throw new IllegalArgumentException("学龄前儿童签证材料标题长度限制1-30个字符");
                    }
                    if (StringUtils.isNotBlank(visaMaterial.getContent()) && (CharCounter.getLength(visaMaterial.getContent()) > 5000 || CharCounter.getLength(visaMaterial.getContent()) < 1)) {
                        throw new IllegalArgumentException("学龄前儿童签证材料内容长度限制1-5000个字符");
                    }
                    if (visaMaterial.getImages() != null && visaMaterial.getImages().size() > 1) {
                        throw new IllegalArgumentException("学龄前儿童签证材料至多上传1张图片");
                    }
                }
            }
        }

        List<VisaProcess> visaProcesses = vo.getProcesses();

        if (!CollectionUtils.isEmpty(visaProcesses)) {
            for (VisaProcess visaProcess : visaProcesses) {
                if (StringUtils.isNotBlank(visaProcess.getTitle()) && (CharCounter.getLength(visaProcess.getTitle()) > 30 || CharCounter.getLength(visaProcess.getTitle()) < 1)) {
                    throw new IllegalArgumentException("办理流程标题长度限制1-30个字符");
                }
                if (StringUtils.isNotBlank(visaProcess.getContent()) && (CharCounter.getLength(visaProcess.getContent()) > 5000 || CharCounter.getLength(visaProcess.getContent()) < 1)) {
                    throw new IllegalArgumentException("办理流程内容长度限制1-5000个字符");
                }
                if (visaProcess.getImages() != null && visaProcess.getImages().size() > 1) {
                    throw new IllegalArgumentException("办理流程至多上传1张图片");
                }
            }
        }

        if (CollectionUtils.isEmpty(vo.getAdditionalInfos())) {

            Map<String, String> additionalInfos = vo.getAdditionalInfos();

            if (StringUtils.isNotBlank(additionalInfos.get(AdditionalRule.BOOKING.name())) && (CharCounter.getLength(additionalInfos.get(AdditionalRule.BOOKING.name())) > 5000 || CharCounter.getLength(additionalInfos.get(AdditionalRule.BOOKING.name())) < 1)) {
                throw new IllegalArgumentException("预订须知长度限制1-5000个字符");
            }

            if (StringUtils.isNotBlank(additionalInfos.get(AdditionalRule.FEE_DETAIL.name())) && (CharCounter.getLength(additionalInfos.get(AdditionalRule.FEE_DETAIL.name())) > 5000 || CharCounter.getLength(additionalInfos.get(AdditionalRule.FEE_DETAIL.name())) < 1)) {
                throw new IllegalArgumentException("费用说明长度限制1-5000个字符");
            }

            if (StringUtils.isNotBlank(additionalInfos.get(AdditionalRule.REFUND_POLICY.name())) && (CharCounter.getLength(additionalInfos.get(AdditionalRule.REFUND_POLICY.name())) > 5000 || CharCounter.getLength(additionalInfos.get(AdditionalRule.REFUND_POLICY.name())) < 1)) {
                throw new IllegalArgumentException("退改政策长度限制1-5000个字符");
            }
        }

    }

}
