package com.platform.modules.quality.web;

import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.platform.common.config.Global;
import com.platform.common.persistence.Page;
import com.platform.common.web.BaseController;
import com.platform.common.utils.DateUtils;
import com.platform.common.utils.StringUtils;
import com.platform.common.utils.excel.ImportExcel;
import com.platform.modules.project.entity.ProSimpleInfo;
import com.platform.modules.project.service.ProSimpleInfoService;
import com.platform.modules.quality.entity.QualityDataIndex;
import com.platform.modules.quality.service.QualityDataIndexService;

/**
 * 质量检测统计数据Controller
 * 
 * @author Mr.Jia
 * @version 2018-02-04
 */
@Controller
@RequestMapping(value = "${adminPath}/quality/qualityDataIndex")
public class QualityDataIndexController extends BaseController {

	@Autowired
	private QualityDataIndexService qualityDataIndexService;
	@Autowired
	private ProSimpleInfoService proSimpleInfoService;

	@ModelAttribute
	public QualityDataIndex get(@RequestParam(required = false) String id) {
		QualityDataIndex entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = qualityDataIndexService.get(id);
		}
		if (entity == null) {
			entity = new QualityDataIndex();
		}
		return entity;
	}

	@RequiresPermissions("quality:qualityDataIndex:view")
	@RequestMapping(value = { "list", "" })
	public String list(QualityDataIndex qualityDataIndex, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Page<QualityDataIndex> page = qualityDataIndexService.findPage(new Page<QualityDataIndex>(request, response),
				qualityDataIndex);
		List<ProSimpleInfo> list = proSimpleInfoService.findAllProjectNames(new ProSimpleInfo());
		model.addAttribute("projectList", list);
		model.addAttribute("page", page);
		return "modules/quality/qualityDataIndexList";
	}
	
	@RequiresPermissions("quality:qualityDataIndex:view")
	@RequestMapping(value = "see")
	public String see(QualityDataIndex qualityDataIndex, Model model) {
		model.addAttribute("qualityDataIndex", qualityDataIndex);
		return "modules/quality/qualityDataIndexSee";
	}

	@RequiresPermissions("quality:qualityDataIndex:view")
	@RequestMapping(value = "form")
	public String form(QualityDataIndex qualityDataIndex, Model model) {
		List<ProSimpleInfo> list = proSimpleInfoService.findAllProjectNames(new ProSimpleInfo());
		model.addAttribute("projectList", list);
		if(StringUtils.isNotBlank(qualityDataIndex.getId())){
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    Date date;
			try {
				date = dateFormat.parse(qualityDataIndex.getCheckDate() + "-01-01");
				qualityDataIndex.setCheckDateForYear(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		model.addAttribute("qualityDataIndex", qualityDataIndex);
		return "modules/quality/qualityDataIndexForm";
	}

	@RequiresPermissions("quality:qualityDataIndex:edit")
	@RequestMapping(value = "save")
	public String save(QualityDataIndex qualityDataIndex, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, qualityDataIndex)) {
			return form(qualityDataIndex, model);
		}
		qualityDataIndexService.save(qualityDataIndex);
		addMessage(redirectAttributes, "保存质量检测统计数据成功");
		return "redirect:" + Global.getAdminPath() + "/quality/qualityDataIndex/?repage";
	}

	@RequiresPermissions("quality:qualityDataIndex:edit")
	@RequestMapping(value = "delete")
	public String delete(QualityDataIndex qualityDataIndex, RedirectAttributes redirectAttributes) {
		qualityDataIndexService.delete(qualityDataIndex);
		addMessage(redirectAttributes, "删除质量检测统计数据成功");
		return "redirect:" + Global.getAdminPath() + "/quality/qualityDataIndex/?repage";
	}

	@RequiresPermissions("quality:qualityDataIndex:edit")
	@RequestMapping(value = "export")
	public String export(HttpServletRequest request, HttpServletResponse response, QualityDataIndex qualityDataIndex,
			Model model, RedirectAttributes redirectAttributes) {

		try {
			ProSimpleInfo proSimpleInfo = proSimpleInfoService.get(qualityDataIndex.getProSimpleInfo().getId());
			String filename = proSimpleInfo.getProjectSimpleName() + "_质量单表检测数据指标_"
					+ DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";

			Map<String, ArrayList> data = getData(qualityDataIndex);

			String contextPath = this.getClass().getClassLoader().getResource("/").getPath();

			ImportExcel importExcel = new ImportExcel(new File(contextPath + "model/template.xlsx"), 1, 0).setData(data)
					.write(response, filename);

			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "下载失败！失败信息：" + e.getMessage());
		}

		return "redirect:" + Global.getAdminPath() + "/quality/qualityDataIndex/?repage";
	}

	public Map<String, ArrayList> getData(QualityDataIndex qualityDataIndex) {
		Map<String, ArrayList> result = new HashMap<String, ArrayList>();
		// key 为一下三个的名称
		ArrayList<Integer> checkPointList = getCheckPointList(qualityDataIndex); // 检测点\
		ArrayList<Integer> passedPointList = getPassedPointList(qualityDataIndex); // 合格点
		// 合格率
		ArrayList<String> passedRateList = new ArrayList<String>(); // 合格率
		for (int index = 0; index < checkPointList.size(); index++) {
			passedRateList.add(getPassedRate(checkPointList.get(index), passedPointList.get(index)));
		}
		result.put("checkPoint", checkPointList);
		result.put("passedPoint", passedPointList);
		result.put("passedRate", passedRateList);
		return result;
	}

	// 路基工程CheckPoint
	public ArrayList<Integer> getEarthworkCheckPointList(QualityDataIndex qualityDataIndex) {

		ArrayList<Integer> earthworkCheckList = new ArrayList<Integer>();
		earthworkCheckList.add(qualityDataIndex.getEarthworkCompaction());
		earthworkCheckList.add(qualityDataIndex.getEarthworkDeflection());
		earthworkCheckList.add(qualityDataIndex.getEarthworkAshDosage());
		earthworkCheckList.add(qualityDataIndex.getDrainageSectionSize());
		earthworkCheckList.add(qualityDataIndex.getDrainagePavedThickness());
		earthworkCheckList.add(qualityDataIndex.getBridgeConcreteStrength());
		earthworkCheckList.add(qualityDataIndex.getBridgeReinforcedThickness());
		earthworkCheckList.add(qualityDataIndex.getBridgeStructureSize());
		earthworkCheckList.add(qualityDataIndex.getBridgeCulvertSpacing());
		earthworkCheckList.add(qualityDataIndex.getBridgeCulvertElevationPosition());
		earthworkCheckList.add(qualityDataIndex.getSupportConcreteStrength());
		earthworkCheckList.add(qualityDataIndex.getSupportSectionSize());
		earthworkCheckList.add(qualityDataIndex.getSubgradeEngineeringOthers());

		return earthworkCheckList;
	}

	public Integer getEarthworkCheckSubTotalPoint(QualityDataIndex qualityDataIndex) {
		Integer subtotal = 0;
		for (Integer checkPoint : getEarthworkCheckPointList(qualityDataIndex)) {
			subtotal += checkPoint;
		}
		return subtotal;
	}

	// 路面工程CheckPoint
	public ArrayList<Integer> getSurfaceCheckPointList(QualityDataIndex qualityDataIndex) {
		ArrayList<Integer> surfaceCheckPointList = new ArrayList<Integer>();

		surfaceCheckPointList.add(qualityDataIndex.getSurfaceCompaction());
		surfaceCheckPointList.add(qualityDataIndex.getSurfaceConcreteRoadStrength());
		surfaceCheckPointList.add(qualityDataIndex.getSurfaceDeflection());
		surfaceCheckPointList.add(qualityDataIndex.getSurfaceAsphaltContent());
		surfaceCheckPointList.add(qualityDataIndex.getSurfacePorosity());
		surfaceCheckPointList.add(qualityDataIndex.getSurfaceThickness());
		surfaceCheckPointList.add(qualityDataIndex.getSurfaceFlatness());
		surfaceCheckPointList.add(qualityDataIndex.getSurfaceCrossSlope());
		surfaceCheckPointList.add(qualityDataIndex.getSurfaceElevation());
		surfaceCheckPointList.add(qualityDataIndex.getSurfaceWaterSeepageCoefficient());
		surfaceCheckPointList.add(qualityDataIndex.getSurfaceSlabHeightDifference());
		surfaceCheckPointList.add(qualityDataIndex.getSurfaceAntiSlipSurface());
		surfaceCheckPointList.add(qualityDataIndex.getGrassrootsLevelCompaction());
		surfaceCheckPointList.add(qualityDataIndex.getGrassrootsSubbaseGrayAmount());
		surfaceCheckPointList.add(qualityDataIndex.getGrassrootsThickness());
		surfaceCheckPointList.add(qualityDataIndex.getGrassrootsUnitIntegrity());
		surfaceCheckPointList.add(qualityDataIndex.getGrassrootsIntensity());
		surfaceCheckPointList.add(qualityDataIndex.getGrassrootsLevelCracks());
		surfaceCheckPointList.add(qualityDataIndex.getGrassrootsCrossSlope());
		surfaceCheckPointList.add(qualityDataIndex.getGrassrootsElevation());
		surfaceCheckPointList.add(qualityDataIndex.getPavementEngineeringOthers());

		return surfaceCheckPointList;
	}

	public Integer getSurfaceCheckSubTotalPoint(QualityDataIndex qualityDataIndex) {
		Integer subtotal = 0;
		for (Integer checkPoint : getSurfaceCheckPointList(qualityDataIndex)) {
			subtotal += checkPoint;
		}
		return subtotal;
	}

	// 桥梁工程bridge
	public ArrayList<Integer> getBridgeCheckPointList(QualityDataIndex qualityDataIndex) {
		ArrayList<Integer> bridgeCheckPointList = new ArrayList<Integer>();

		bridgeCheckPointList.add(qualityDataIndex.getLowerstructureConcreteStrength());
		bridgeCheckPointList.add(qualityDataIndex.getLowerstructureReinforcedProtectiveThickness());
		bridgeCheckPointList.add(qualityDataIndex.getLowerstructurePierVerticality());
		bridgeCheckPointList.add(qualityDataIndex.getLowerstructureStructureSize());
		bridgeCheckPointList.add(qualityDataIndex.getLowerstructureReinforcedSpacing());
		bridgeCheckPointList.add(qualityDataIndex.getLowerstructureElevationPlaneLocation());
		bridgeCheckPointList.add(qualityDataIndex.getSuperstructureConcreteStrength());
		bridgeCheckPointList.add(qualityDataIndex.getSuperstructureReinforcedProtectiveThickness());
		bridgeCheckPointList.add(qualityDataIndex.getSuperstructureStructureSize());
		bridgeCheckPointList.add(qualityDataIndex.getSuperstructureReinforcedSpacing());
		bridgeCheckPointList.add(qualityDataIndex.getSuperstructureElevationPlaneLocation());
		bridgeCheckPointList.add(qualityDataIndex.getSuperstructurePrestressedHoleCoordinates());
		bridgeCheckPointList.add(qualityDataIndex.getBridgeWidth());
		bridgeCheckPointList.add(qualityDataIndex.getBridgeThickness());
		bridgeCheckPointList.add(qualityDataIndex.getBridgeSlope());
		bridgeCheckPointList.add(qualityDataIndex.getHardenedConcreteChlorideIonContent());
		bridgeCheckPointList.add(qualityDataIndex.getHardenedConcreteAlkaliContent());
		bridgeCheckPointList.add(qualityDataIndex.getConcreteAdditives());
		bridgeCheckPointList.add(qualityDataIndex.getAnchorsPrestressing());
		bridgeCheckPointList.add(qualityDataIndex.getPlateRubberBearingInstallationQuality());
		bridgeCheckPointList.add(qualityDataIndex.getDeckTopSurfaceElevation());
		bridgeCheckPointList.add(qualityDataIndex.getCrackWidth());
		bridgeCheckPointList.add(qualityDataIndex.getPileFoundation());
		bridgeCheckPointList.add(qualityDataIndex.getBridgeEngineeringOthers());

		return bridgeCheckPointList;
	}

	public Integer getBridgeCheckSubTotalPoint(QualityDataIndex qualityDataIndex) {
		Integer subtotal = 0;
		for (Integer checkPoint : getBridgeCheckPointList(qualityDataIndex)) {
			subtotal += checkPoint;
		}
		return subtotal;
	}

	// 隧道工程tunnel
	public ArrayList<Integer> getTunnelCheckPointList(QualityDataIndex qualityDataIndex) {
		ArrayList<Integer> tunnelCheckPointList = new ArrayList<Integer>(); // 检测点

		tunnelCheckPointList.add(qualityDataIndex.getLiningSupportConcreteStrength());
		tunnelCheckPointList.add(qualityDataIndex.getLiningSupportLiningThickness());
		tunnelCheckPointList.add(qualityDataIndex.getLiningSupportFlatness());
		tunnelCheckPointList.add(qualityDataIndex.getLiningSupportAnchorDrawing());
		tunnelCheckPointList.add(qualityDataIndex.getAnchorSpacingLengthGrouting());
		tunnelCheckPointList.add(qualityDataIndex.getClearance());
		tunnelCheckPointList.add(qualityDataIndex.getSectionOutline());
		tunnelCheckPointList.add(qualityDataIndex.getArchesSpacing());
		tunnelCheckPointList.add(qualityDataIndex.getReinforcedSpacing());
		tunnelCheckPointList.add(qualityDataIndex.getEmpties());
		tunnelCheckPointList.add(qualityDataIndex.getWaterproofBoardWeldingWide());
		tunnelCheckPointList.add(qualityDataIndex.getLeadSmallNumIndirect());
		tunnelCheckPointList.add(qualityDataIndex.getTunnelPavement());
		tunnelCheckPointList.add(qualityDataIndex.getTunnelEngineeringOthers());

		return tunnelCheckPointList;
	}

	public Integer getTunnelCheckSubTotalPoint(QualityDataIndex qualityDataIndex) {
		Integer subtotal = 0;
		for (Integer checkPoint : getTunnelCheckPointList(qualityDataIndex)) {
			subtotal += checkPoint;
		}
		return subtotal;
	}

	// 原材料material
	public ArrayList<Integer> getMaterialCheckPointList(QualityDataIndex qualityDataIndex) {
		ArrayList<Integer> materialCheckPointList = new ArrayList<Integer>(); // 检测点

		materialCheckPointList.add(qualityDataIndex.getReinforced());
		materialCheckPointList.add(qualityDataIndex.getCement());
		materialCheckPointList.add(qualityDataIndex.getAsphalt());
		materialCheckPointList.add(qualityDataIndex.getLime());
		materialCheckPointList.add(qualityDataIndex.getGravel());
		materialCheckPointList.add(qualityDataIndex.getSand());
		materialCheckPointList.add(qualityDataIndex.getMineralPowder());
		materialCheckPointList.add(qualityDataIndex.getRubberBearings());
		materialCheckPointList.add(qualityDataIndex.getAnchor());
		materialCheckPointList.add(qualityDataIndex.getSplicingBolts());
		materialCheckPointList.add(qualityDataIndex.getGeotextile());
		materialCheckPointList.add(qualityDataIndex.getWaterproofBoard());
		materialCheckPointList.add(qualityDataIndex.getDrain());
		materialCheckPointList.add(qualityDataIndex.getSlurry());
		materialCheckPointList.add(qualityDataIndex.getMaterialsOthers());

		return materialCheckPointList;
	}

	public Integer getMaterialCheckSubTotalPoint(QualityDataIndex qualityDataIndex) {
		Integer subtotal = 0;
		for (Integer checkPoint : getMaterialCheckPointList(qualityDataIndex)) {
			subtotal += checkPoint;
		}
		return subtotal;
	}

	// 交通安全设施traffic
	public ArrayList<Integer> getTrafficCheckPointList(QualityDataIndex qualityDataIndex) {
		ArrayList<Integer> trafficCheckPointList = new ArrayList<Integer>();
		trafficCheckPointList.add(qualityDataIndex.getComponentBaseThickness());
		trafficCheckPointList.add(qualityDataIndex.getComponentCoatingThickness());
		trafficCheckPointList.add(qualityDataIndex.getFencebeamCenterHeight());
		trafficCheckPointList.add(qualityDataIndex.getFenceColumnEmbeddedDepth());
		trafficCheckPointList.add(qualityDataIndex.getStitchingBoltTensileStrength());
		trafficCheckPointList.add(qualityDataIndex.getPillarWallThickness());
		trafficCheckPointList.add(qualityDataIndex.getConcreteFenceStrength());
		trafficCheckPointList.add(qualityDataIndex.getWavePlateThickness());
		trafficCheckPointList.add(qualityDataIndex.getSignBoardClearance());
		trafficCheckPointList.add(qualityDataIndex.getSignThickness());
		trafficCheckPointList.add(qualityDataIndex.getSignReverseReflection());
		trafficCheckPointList.add(qualityDataIndex.getTrafficOthers());

		return trafficCheckPointList;
	}

	public Integer getTrafficCheckSubTotalPoint(QualityDataIndex qualityDataIndex) {
		Integer subtotal = 0;
		for (Integer checkPoint : getTrafficCheckPointList(qualityDataIndex)) {
			subtotal += checkPoint;
		}
		return subtotal;
	}

	// 机电工程 electrical
	public ArrayList<Integer> getElectricalCheckPointList(QualityDataIndex qualityDataIndex) {
		ArrayList<Integer> electricalCheckPointList = new ArrayList<Integer>();

		electricalCheckPointList.add(qualityDataIndex.getNetworkQuality());
		electricalCheckPointList.add(qualityDataIndex.getCoatingThickness());
		electricalCheckPointList.add(qualityDataIndex.getInsulationResistance());
		electricalCheckPointList.add(qualityDataIndex.getGroundingResistance());
		electricalCheckPointList.add(qualityDataIndex.getPowerFactors());
		electricalCheckPointList.add(qualityDataIndex.getBlowerClearance());
		electricalCheckPointList.add(qualityDataIndex.getMeOthers());
		return electricalCheckPointList;
	}

	public Integer getElectricalCheckSubTotalPoint(QualityDataIndex qualityDataIndex) {
		Integer subtotal = 0;
		for (Integer checkPoint : getElectricalCheckPointList(qualityDataIndex)) {
			subtotal += checkPoint;
		}
		return subtotal;
	}

	// 总计检测点
	public ArrayList<Integer> getTotalCheckPointList(QualityDataIndex qualityDataIndex) {
		ArrayList<Integer> totalCheckPointList = new ArrayList<Integer>();

		totalCheckPointList.add(getEarthworkCheckSubTotalPoint(qualityDataIndex));
		totalCheckPointList.add(getSurfaceCheckSubTotalPoint(qualityDataIndex));
		totalCheckPointList.add(getBridgeCheckSubTotalPoint(qualityDataIndex));
		totalCheckPointList.add(getTunnelCheckSubTotalPoint(qualityDataIndex));
		totalCheckPointList.add(getMaterialCheckSubTotalPoint(qualityDataIndex));
		totalCheckPointList.add(getTrafficCheckSubTotalPoint(qualityDataIndex));
		totalCheckPointList.add(getElectricalCheckSubTotalPoint(qualityDataIndex));

		return totalCheckPointList;
	}

	// 关键指标检测点
	public ArrayList<Integer> getKeyCheckPointList(QualityDataIndex qualityDataIndex) {
		ArrayList<Integer> keyCheckPointList = new ArrayList<Integer>();
		// 路基
		keyCheckPointList.add(qualityDataIndex.getEarthworkCompaction());
		keyCheckPointList.add(qualityDataIndex.getEarthworkDeflection());
		keyCheckPointList.add(qualityDataIndex.getBridgeConcreteStrength());
		keyCheckPointList.add(qualityDataIndex.getSupportConcreteStrength());
		keyCheckPointList.add(qualityDataIndex.getSupportSectionSize());
		// 路面
		keyCheckPointList.add(qualityDataIndex.getSurfaceCompaction());
		keyCheckPointList.add(qualityDataIndex.getSurfaceConcreteRoadStrength());
		keyCheckPointList.add(qualityDataIndex.getSurfaceDeflection());
		keyCheckPointList.add(qualityDataIndex.getSurfaceThickness());
		keyCheckPointList.add(qualityDataIndex.getSurfaceFlatness());
		keyCheckPointList.add(qualityDataIndex.getGrassrootsLevelCompaction());
		keyCheckPointList.add(qualityDataIndex.getGrassrootsUnitIntegrity());
		keyCheckPointList.add(qualityDataIndex.getGrassrootsIntensity());

		// 桥梁
		keyCheckPointList.add(qualityDataIndex.getLowerstructureConcreteStrength());
		keyCheckPointList.add(qualityDataIndex.getSuperstructureConcreteStrength());
		keyCheckPointList.add(qualityDataIndex.getBridgeWidth());
		keyCheckPointList.add(qualityDataIndex.getBridgeThickness());
		keyCheckPointList.add(qualityDataIndex.getBridgeSlope());
		// 隧道
		keyCheckPointList.add(qualityDataIndex.getLiningSupportConcreteStrength());
		keyCheckPointList.add(qualityDataIndex.getLiningSupportLiningThickness());
		keyCheckPointList.add(qualityDataIndex.getAnchorSpacingLengthGrouting());
		keyCheckPointList.add(qualityDataIndex.getClearance());
		// 原材料
		keyCheckPointList.addAll(getMaterialCheckPointList(qualityDataIndex));
		// 交通
		keyCheckPointList.add(qualityDataIndex.getFencebeamCenterHeight());
		keyCheckPointList.add(qualityDataIndex.getPillarWallThickness());
		keyCheckPointList.add(qualityDataIndex.getConcreteFenceStrength());
		keyCheckPointList.add(qualityDataIndex.getWavePlateThickness());
		return keyCheckPointList;
	}

	/***
	 * getCheckPointList检测点所有指标 包括小计、总计、 关键指标
	 * 
	 * @param qualityDataIndex
	 * @return ArrayList
	 */
	public ArrayList<Integer> getCheckPointList(QualityDataIndex qualityDataIndex) {
		ArrayList<Integer> checkPointList = new ArrayList<Integer>(); // 检测点
		// 路基
		checkPointList.addAll(getEarthworkCheckPointList(qualityDataIndex));
		checkPointList.add(getEarthworkCheckSubTotalPoint(qualityDataIndex));
		// 路面
		checkPointList.addAll(getSurfaceCheckPointList(qualityDataIndex));
		checkPointList.add(getSurfaceCheckSubTotalPoint(qualityDataIndex));
		// 桥梁
		checkPointList.addAll(getBridgeCheckPointList(qualityDataIndex));
		checkPointList.add(getBridgeCheckSubTotalPoint(qualityDataIndex));
		// 隧道
		checkPointList.addAll(getTunnelCheckPointList(qualityDataIndex));
		checkPointList.add(getTunnelCheckSubTotalPoint(qualityDataIndex));
		// 原材料
		checkPointList.addAll(getMaterialCheckPointList(qualityDataIndex));
		checkPointList.add(getMaterialCheckSubTotalPoint(qualityDataIndex));
		// 交通安全
		checkPointList.addAll(getTrafficCheckPointList(qualityDataIndex));
		checkPointList.add(getTrafficCheckSubTotalPoint(qualityDataIndex));
		// 机电工程
		checkPointList.addAll(getElectricalCheckPointList(qualityDataIndex));
		checkPointList.add(getElectricalCheckSubTotalPoint(qualityDataIndex));

		// 总计
		Integer totalPoint = 0;
		for (Integer total : getTotalCheckPointList(qualityDataIndex)) {
			totalPoint += total;
		}
		checkPointList.add(totalPoint);
		// 关键指标
		Integer keyPoint = 0;
		for (Integer key : getKeyCheckPointList(qualityDataIndex)) {
			keyPoint += key;
		}
		checkPointList.add(keyPoint);
		return checkPointList;
	}

	public ArrayList<Integer> getPassedPointList(QualityDataIndex qualityDataIndex) {
		ArrayList<Integer> passedPointList = new ArrayList<Integer>(); // 合格点

		//
		// 路基
		passedPointList.addAll(getEarthworkPassPointList(qualityDataIndex));
		passedPointList.add(getEarthworkPassSubTotalPoint(qualityDataIndex));
		// 路面
		passedPointList.addAll(getSurfacePassPointList(qualityDataIndex));
		passedPointList.add(getSurfacePassSubTotalPoint(qualityDataIndex));
		// 桥梁
		passedPointList.addAll(getBridgePassPointList(qualityDataIndex));
		passedPointList.add(getBridgePassSubTotalPoint(qualityDataIndex));
		// 隧道
		passedPointList.addAll(getTunnelPassPointList(qualityDataIndex));
		passedPointList.add(getTunnelPassSubTotalPoint(qualityDataIndex));
		// 原材料
		passedPointList.addAll(getMaterialPassPointList(qualityDataIndex));
		passedPointList.add(getMaterialPassSubTotalPoint(qualityDataIndex));
		// 交通安全
		passedPointList.addAll(getTrafficPassPointList(qualityDataIndex));
		passedPointList.add(getTrafficPassSubTotalPoint(qualityDataIndex));
		// 机电工程
		passedPointList.addAll(getElectricalPassPointList(qualityDataIndex));
		passedPointList.add(getElectricalPassSubTotalPoint(qualityDataIndex));

		// 总计
		Integer totalPoint = 0;
		for (Integer total : getTotalPassPointList(qualityDataIndex)) {
			totalPoint += total;
		}
		passedPointList.add(totalPoint);
		// 关键指标
		Integer keyPoint = 0;
		for (Integer key : getKeyPassPointList(qualityDataIndex)) {
			keyPoint += key;
		}
		passedPointList.add(keyPoint);

		return passedPointList;
	}

	// 路基工程PassPoint
	public ArrayList<Integer> getEarthworkPassPointList(QualityDataIndex qualityDataIndex) {

		ArrayList<Integer> earthworkPassList = new ArrayList<Integer>();

		earthworkPassList.add(qualityDataIndex.getEarthworkCompactionPassed());
		earthworkPassList.add(qualityDataIndex.getEarthworkDeflectionPassed());
		earthworkPassList.add(qualityDataIndex.getEarthworkAshDosagePassed());
		earthworkPassList.add(qualityDataIndex.getDrainageSectionSizePassed());
		earthworkPassList.add(qualityDataIndex.getDrainagePavedThicknessPassed());
		earthworkPassList.add(qualityDataIndex.getBridgeConcreteStrengthPassed());
		earthworkPassList.add(qualityDataIndex.getBridgeReinforcedThicknessPassed());
		earthworkPassList.add(qualityDataIndex.getBridgeStructureSizePassed());
		earthworkPassList.add(qualityDataIndex.getBridgeCulvertSpacingPassed());
		earthworkPassList.add(qualityDataIndex.getBridgeCulvertElevationPositionPassed());
		earthworkPassList.add(qualityDataIndex.getSupportConcreteStrengthPassed());
		earthworkPassList.add(qualityDataIndex.getSupportSectionSizePassed());
		earthworkPassList.add(qualityDataIndex.getSubgradeEngineeringOthersPassed());

		return earthworkPassList;
	}

	public Integer getEarthworkPassSubTotalPoint(QualityDataIndex qualityDataIndex) {
		Integer subtotal = 0;
		for (Integer passPoint : getEarthworkPassPointList(qualityDataIndex)) {
			subtotal += passPoint;
		}
		return subtotal;
	}

	// 路面工程PassPoint
	public ArrayList<Integer> getSurfacePassPointList(QualityDataIndex qualityDataIndex) {
		ArrayList<Integer> surfacePassPointList = new ArrayList<Integer>();

		surfacePassPointList.add(qualityDataIndex.getSurfaceCompactionPassed());
		surfacePassPointList.add(qualityDataIndex.getSurfaceConcreteRoadStrengthPassed());
		surfacePassPointList.add(qualityDataIndex.getSurfaceDeflectionPassed());
		surfacePassPointList.add(qualityDataIndex.getSurfaceAsphaltContentPassed());
		surfacePassPointList.add(qualityDataIndex.getSurfacePorosityPassed());
		surfacePassPointList.add(qualityDataIndex.getSurfaceThicknessPassed());
		surfacePassPointList.add(qualityDataIndex.getSurfaceFlatnessPassed());
		surfacePassPointList.add(qualityDataIndex.getSurfaceCrossSlopePassed());
		surfacePassPointList.add(qualityDataIndex.getSurfaceElevationPassed());
		surfacePassPointList.add(qualityDataIndex.getSurfaceWaterSeepageCoefficientPassed());
		surfacePassPointList.add(qualityDataIndex.getSurfaceSlabHeightDifferencePassed());
		surfacePassPointList.add(qualityDataIndex.getSurfaceAntiSlipSurfacePassed());
		surfacePassPointList.add(qualityDataIndex.getGrassrootsLevelCompactionPassed());
		surfacePassPointList.add(qualityDataIndex.getGrassrootsSubbaseGrayAmountPassed());
		surfacePassPointList.add(qualityDataIndex.getGrassrootsThicknessPassed());
		surfacePassPointList.add(qualityDataIndex.getGrassrootsUnitIntegrityPassed());
		surfacePassPointList.add(qualityDataIndex.getGrassrootsIntensityPassed());
		surfacePassPointList.add(qualityDataIndex.getGrassrootsLevelCracksPassed());
		surfacePassPointList.add(qualityDataIndex.getGrassrootsCrossSlopePassed());
		surfacePassPointList.add(qualityDataIndex.getGrassrootsElevationPassed());
		surfacePassPointList.add(qualityDataIndex.getPavementEngineeringOthersPassed());

		return surfacePassPointList;
	}

	public Integer getSurfacePassSubTotalPoint(QualityDataIndex qualityDataIndex) {
		Integer subtotal = 0;
		for (Integer passPoint : getSurfacePassPointList(qualityDataIndex)) {
			subtotal += passPoint;
		}
		return subtotal;
	}

	// 桥梁工程bridge
	public ArrayList<Integer> getBridgePassPointList(QualityDataIndex qualityDataIndex) {
		ArrayList<Integer> bridgePassPointList = new ArrayList<Integer>();

		bridgePassPointList.add(qualityDataIndex.getLowerstructureConcreteStrengthPassed());
		bridgePassPointList.add(qualityDataIndex.getLowerstructureReinforcedProtectiveThicknessPassed());
		bridgePassPointList.add(qualityDataIndex.getLowerstructurePierVerticalityPassed());
		bridgePassPointList.add(qualityDataIndex.getLowerstructureStructureSizePassed());
		bridgePassPointList.add(qualityDataIndex.getLowerstructureReinforcedSpacingPassed());
		bridgePassPointList.add(qualityDataIndex.getLowerstructureElevationPlaneLocationPassed());
		bridgePassPointList.add(qualityDataIndex.getSuperstructureConcreteStrengthPassed());
		bridgePassPointList.add(qualityDataIndex.getSuperstructureReinforcedProtectiveThicknessPassed());
		bridgePassPointList.add(qualityDataIndex.getSuperstructureStructureSizePassed());
		bridgePassPointList.add(qualityDataIndex.getSuperstructureReinforcedSpacingPassed());
		bridgePassPointList.add(qualityDataIndex.getSuperstructureElevationPlaneLocationPassed());
		bridgePassPointList.add(qualityDataIndex.getSuperstructurePrestressedHoleCoordinatesPassed());
		bridgePassPointList.add(qualityDataIndex.getBridgeWidthPassed());
		bridgePassPointList.add(qualityDataIndex.getBridgeThicknessPassed());
		bridgePassPointList.add(qualityDataIndex.getBridgeSlopePassed());
		bridgePassPointList.add(qualityDataIndex.getHardenedConcreteChlorideIonContentPassed());
		bridgePassPointList.add(qualityDataIndex.getHardenedConcreteAlkaliContentPassed());
		bridgePassPointList.add(qualityDataIndex.getConcreteAdditivesPassed());
		bridgePassPointList.add(qualityDataIndex.getAnchorsPrestressingPassed());
		bridgePassPointList.add(qualityDataIndex.getPlateRubberBearingInstallationQualityPassed());
		bridgePassPointList.add(qualityDataIndex.getDeckTopSurfaceElevationPassed());
		bridgePassPointList.add(qualityDataIndex.getCrackWidthPassed());
		bridgePassPointList.add(qualityDataIndex.getPileFoundationPassed());
		bridgePassPointList.add(qualityDataIndex.getBridgeEngineeringOthersPassed());

		return bridgePassPointList;
	}

	public Integer getBridgePassSubTotalPoint(QualityDataIndex qualityDataIndex) {
		Integer subtotal = 0;
		for (Integer passPoint : getBridgePassPointList(qualityDataIndex)) {
			subtotal += passPoint;
		}
		return subtotal;
	}

	// 隧道工程tunnel PassPoint
	public ArrayList<Integer> getTunnelPassPointList(QualityDataIndex qualityDataIndex) {
		ArrayList<Integer> tunnelPassPointList = new ArrayList<Integer>();

		tunnelPassPointList.add(qualityDataIndex.getLiningSupportConcreteStrengthPassed());
		tunnelPassPointList.add(qualityDataIndex.getLiningSupportLiningThicknessPassed());
		tunnelPassPointList.add(qualityDataIndex.getLiningSupportFlatnessPassed());
		tunnelPassPointList.add(qualityDataIndex.getLiningSupportAnchorDrawingPassed());
		tunnelPassPointList.add(qualityDataIndex.getAnchorSpacingLengthGroutingPassed());
		tunnelPassPointList.add(qualityDataIndex.getClearancePassed());
		tunnelPassPointList.add(qualityDataIndex.getSectionOutlinePassed());
		tunnelPassPointList.add(qualityDataIndex.getArchesSpacingPassed());
		tunnelPassPointList.add(qualityDataIndex.getReinforcedSpacingPassed());
		tunnelPassPointList.add(qualityDataIndex.getEmptiesPassed());
		tunnelPassPointList.add(qualityDataIndex.getWaterproofBoardWeldingWidePassed());
		tunnelPassPointList.add(qualityDataIndex.getLeadSmallNumIndirectPassed());
		tunnelPassPointList.add(qualityDataIndex.getTunnelPavementPassed());
		tunnelPassPointList.add(qualityDataIndex.getTunnelEngineeringOthersPassed());

		return tunnelPassPointList;
	}

	public Integer getTunnelPassSubTotalPoint(QualityDataIndex qualityDataIndex) {
		Integer subtotal = 0;
		for (Integer passkPoint : getTunnelPassPointList(qualityDataIndex)) {
			subtotal += passkPoint;
		}
		return subtotal;
	}

	// 原材料material PassPoint
	public ArrayList<Integer> getMaterialPassPointList(QualityDataIndex qualityDataIndex) {
		ArrayList<Integer> materialPassPointList = new ArrayList<Integer>();
		materialPassPointList.add(qualityDataIndex.getReinforcedPassed());
		materialPassPointList.add(qualityDataIndex.getCementPassed());
		materialPassPointList.add(qualityDataIndex.getAsphaltPassed());
		materialPassPointList.add(qualityDataIndex.getLimePassed());
		materialPassPointList.add(qualityDataIndex.getGravelsandPassed());
		materialPassPointList.add(qualityDataIndex.getSandPassed());
		materialPassPointList.add(qualityDataIndex.getMineralPowderPassed());
		materialPassPointList.add(qualityDataIndex.getRubberBearingsPassed());
		materialPassPointList.add(qualityDataIndex.getAnchorPassed());
		materialPassPointList.add(qualityDataIndex.getSplicingBoltsPassed());
		materialPassPointList.add(qualityDataIndex.getGeotextilePassed());
		materialPassPointList.add(qualityDataIndex.getWaterproofBoardPassed());
		materialPassPointList.add(qualityDataIndex.getDrainPassed());
		materialPassPointList.add(qualityDataIndex.getSlurryPassed());
		materialPassPointList.add(qualityDataIndex.getMaterialsOthersPassed());

		return materialPassPointList;
	}

	public Integer getMaterialPassSubTotalPoint(QualityDataIndex qualityDataIndex) {
		Integer subtotal = 0;
		for (Integer passkPoint : getMaterialPassPointList(qualityDataIndex)) {
			subtotal += passkPoint;
		}
		return subtotal;
	}

	// 交通安全设施traffic
	public ArrayList<Integer> getTrafficPassPointList(QualityDataIndex qualityDataIndex) {
		ArrayList<Integer> trafficPassPointList = new ArrayList<Integer>();

		trafficPassPointList.add(qualityDataIndex.getComponentBaseThicknessPassed());
		trafficPassPointList.add(qualityDataIndex.getComponentCoatingThicknessPassed());
		trafficPassPointList.add(qualityDataIndex.getFencebeamCenterHeightPassed());
		trafficPassPointList.add(qualityDataIndex.getFenceColumnEmbeddedDepthPassed());
		trafficPassPointList.add(qualityDataIndex.getStitchingBoltTensileStrengthPassed());
		trafficPassPointList.add(qualityDataIndex.getPillarWallThicknessPassed());
		trafficPassPointList.add(qualityDataIndex.getConcreteFenceStrengthPassed());
		trafficPassPointList.add(qualityDataIndex.getWavePlateThicknessPassed());
		trafficPassPointList.add(qualityDataIndex.getSignBoardClearancePassed());
		trafficPassPointList.add(qualityDataIndex.getSignThicknessPassed());
		trafficPassPointList.add(qualityDataIndex.getSignReverseReflectionPassed());
		trafficPassPointList.add(qualityDataIndex.getTrafficOthersPassed());

		return trafficPassPointList;
	}

	public Integer getTrafficPassSubTotalPoint(QualityDataIndex qualityDataIndex) {
		Integer subtotal = 0;
		for (Integer passPoint : getTrafficPassPointList(qualityDataIndex)) {
			subtotal += passPoint;
		}
		return subtotal;
	}

	// 机电工程 electrical
	public ArrayList<Integer> getElectricalPassPointList(QualityDataIndex qualityDataIndex) {
		ArrayList<Integer> electricalPassPointList = new ArrayList<Integer>();

		electricalPassPointList.add(qualityDataIndex.getNetworkQualityPassed());
		electricalPassPointList.add(qualityDataIndex.getCoatingThicknessPassed());
		electricalPassPointList.add(qualityDataIndex.getInsulationResistancePassed());
		electricalPassPointList.add(qualityDataIndex.getGroundingResistancePassed());
		electricalPassPointList.add(qualityDataIndex.getPowerFactorsPassed());
		electricalPassPointList.add(qualityDataIndex.getBlowerClearancePassed());
		electricalPassPointList.add(qualityDataIndex.getMeOthersPassed());

		return electricalPassPointList;
	}

	public Integer getElectricalPassSubTotalPoint(QualityDataIndex qualityDataIndex) {
		Integer subtotal = 0;
		for (Integer passPoint : getElectricalPassPointList(qualityDataIndex)) {
			subtotal += passPoint;
		}
		return subtotal;
	}

	// 总计合格点
	public ArrayList<Integer> getTotalPassPointList(QualityDataIndex qualityDataIndex) {
		ArrayList<Integer> totalPassPointList = new ArrayList<Integer>();

		totalPassPointList.add(getEarthworkPassSubTotalPoint(qualityDataIndex));
		totalPassPointList.add(getSurfacePassSubTotalPoint(qualityDataIndex));
		totalPassPointList.add(getBridgePassSubTotalPoint(qualityDataIndex));
		totalPassPointList.add(getTunnelPassSubTotalPoint(qualityDataIndex));
		totalPassPointList.add(getMaterialPassSubTotalPoint(qualityDataIndex));
		totalPassPointList.add(getTrafficPassSubTotalPoint(qualityDataIndex));
		totalPassPointList.add(getElectricalPassSubTotalPoint(qualityDataIndex));

		return totalPassPointList;
	}

	// 关键指标合格点
	public ArrayList<Integer> getKeyPassPointList(QualityDataIndex qualityDataIndex) {
		ArrayList<Integer> keyPassPointList = new ArrayList<Integer>();
		// 路基
		keyPassPointList.add(qualityDataIndex.getEarthworkCompactionPassed());
		keyPassPointList.add(qualityDataIndex.getEarthworkDeflectionPassed());
		keyPassPointList.add(qualityDataIndex.getBridgeConcreteStrengthPassed());
		keyPassPointList.add(qualityDataIndex.getSupportConcreteStrengthPassed());
		keyPassPointList.add(qualityDataIndex.getSupportSectionSizePassed());
		// 路面
		keyPassPointList.add(qualityDataIndex.getSurfaceCompactionPassed());
		keyPassPointList.add(qualityDataIndex.getSurfaceConcreteRoadStrengthPassed());
		keyPassPointList.add(qualityDataIndex.getSurfaceDeflectionPassed());
		keyPassPointList.add(qualityDataIndex.getSurfaceThicknessPassed());
		keyPassPointList.add(qualityDataIndex.getSurfaceFlatnessPassed());
		keyPassPointList.add(qualityDataIndex.getGrassrootsLevelCompactionPassed());
		keyPassPointList.add(qualityDataIndex.getGrassrootsUnitIntegrityPassed());
		keyPassPointList.add(qualityDataIndex.getGrassrootsIntensityPassed());

		// 桥梁
		keyPassPointList.add(qualityDataIndex.getLowerstructureConcreteStrengthPassed());
		keyPassPointList.add(qualityDataIndex.getSuperstructureConcreteStrengthPassed());
		keyPassPointList.add(qualityDataIndex.getBridgeWidthPassed());
		keyPassPointList.add(qualityDataIndex.getBridgeThicknessPassed());
		keyPassPointList.add(qualityDataIndex.getBridgeSlopePassed());
		// 隧道
		keyPassPointList.add(qualityDataIndex.getLiningSupportConcreteStrengthPassed());
		keyPassPointList.add(qualityDataIndex.getLiningSupportLiningThicknessPassed());
		keyPassPointList.add(qualityDataIndex.getAnchorSpacingLengthGroutingPassed());
		keyPassPointList.add(qualityDataIndex.getClearancePassed());
		// 原材料
		keyPassPointList.addAll(getMaterialPassPointList(qualityDataIndex));
		// 交通
		keyPassPointList.add(qualityDataIndex.getFencebeamCenterHeightPassed());
		keyPassPointList.add(qualityDataIndex.getPillarWallThicknessPassed());
		keyPassPointList.add(qualityDataIndex.getConcreteFenceStrengthPassed());
		keyPassPointList.add(qualityDataIndex.getWavePlateThicknessPassed());
		return keyPassPointList;
	}

	public String getPassedRate(Integer checkPoint, Integer passedPoint) {
		DecimalFormat format = new DecimalFormat("#0.00");   
		String rate = "0.00%";
		if (checkPoint != 0) {
			rate = format.format((Double.valueOf(String.valueOf(passedPoint))  / Double.valueOf(String.valueOf(checkPoint))) * 100) + "%";
			return rate;
		}
		return rate;
	}

	public boolean getIsNaN(Integer integer) {
		return integer == 0 ? true : false;
	}

	public static void main(String[] args) {
		System.out.println(123);
	}
}