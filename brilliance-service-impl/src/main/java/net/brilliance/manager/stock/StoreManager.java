/*
* Copyright 2017, Bui Quy Duc
* by the @authors tag. See the LICENCE in the distribution for a
* full listing of individual contributors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package net.brilliance.manager.stock;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.brilliance.common.logging.GlobalLoggerFactory;
import net.brilliance.domain.entity.stock.Store;
import net.brilliance.framework.manager.BaseManager;
import net.brilliance.framework.repository.BaseRepository;
import net.brilliance.repository.stock.StoreRepository;

/**
 * Store service implementation. Provides implementation of the measure unit
 * 
 * @author ducbq
 *
 */
@Service
@Transactional
public class StoreManager extends BaseManager<Store, Long> {
	private static final long serialVersionUID = -3621765663559056110L;

	final Logger logger = GlobalLoggerFactory.getLogger(this.getClass());

	@Inject
	private StoreRepository repository;

	@Override
	protected BaseRepository<Store, Long> getRepository() {
		return this.repository;
	}

	@Override
	protected Page<Store> performSearch(String keyword, Pageable pageable) {
		return repository.search(keyword, pageable);
	}

	public Store getByName(String name) {
		return repository.findByName(name);
	}

	public Store getByCode(String code) {
		return repository.findByCode(code);
	}

	public Page<Store> searchStore(String keyword, Pageable pageable){
		return repository.search(keyword, pageable);
	}

	public void setupMasterData(){
		repository.save(Store.getInstance("VNNCY","CTCP DVHH S.GON SCSC", "30 đường Phan Thúc Duyên, Q.Tân Bình, TP. Hồ Chí Minh", "Công ty CP dịch vụ hàng hoá Sài Gòn SCSC"));
		repository.save(Store.getInstance("VNNCZ","CT DVHH TSN (TCS)", "46-48, đường Hậu Giang, F4, Q. Tân Bình, TP. Hồ Chí Minh", "Công ty TNHH dịch vụ hàng hoá Tân Sơn Nhất (TCS)"));
		repository.save(Store.getInstance("VNNDA","CTLD VT HH VIET NHAT", "Lô 6, KCN Quang Minh, Mê Linh, HN (Vĩnh Phúc)", "Cty TNHH Dịch vụ vận tải Việt Nhật số 2 "));
		repository.save(Store.getInstance("VNNDB","CT T.VAN THANG LONG", "Lô E-4A KCN Thăng Long, Hà Nội", "Cty TNHH Tiếp vận Thăng Long "));
		repository.save(Store.getInstance("VNNDC","CT N.LUC TMQT HA NOI", "Địa điểm thông quan ICD Mỹ Đình, Hà Nội", "Công ty cung ứng nhân lực và thương mại quốc tế Hà Nội"));
		repository.save(Store.getInstance("VNNDD","NIPPONEXPRESS VN(HN)", "Lô 38B, KCN Quang Minh, Mê Linh, HN", "Công ty LD TNHH Nippon Express VN – Chi nhánh HN"));
		repository.save(Store.getInstance("VNNDE","CTTNHH Q.TE DUC YEN", "Thửa 660, KCN Quang Minh, Mê Linh, HN", "Kho ngoại quan Công ty TNHH Quốc tế Đức Yên    "));
		repository.save(Store.getInstance("VNNDF","CTCP TMXNK THANH LOI", "Khu CN Nội Bài, Sóc Sơn – Hà Nội", "Kho ngoại quan Công ty cổ phần thương mại XNK Thành Lợi "));
		repository.save(Store.getInstance("VNNDG","CTY CP KHO VAN ALS", "Cảng sân bay Nội Bài, Sóc Sơn, Hà Nội", "Kho ngoại quan Công ty cổ phần giao nhận kho vận hàng không"));
		repository.save(Store.getInstance("VNNDH","CT NYK LOGISTICS VN", "KCN Yên Phong 1, tỉnh Bắc Ninh.", "Công ty TNHH NYK Logistics VN"));
		repository.save(Store.getInstance("VNNDI","CT PT D.THI KINH BAC", "Lô C3, KCN Quế Võ, Bắc Ninh", "Cty phát triển đô thị Kinh Bắc"));
		repository.save(Store.getInstance("VNNDJ","SAGAWA EXPRESS (HN)", "Lô D, KCN Quế Võ, Bắc Ninh", "Cty TNHH Sagawa Express Việt Nam tại Hà Nội -Quế Võ"));
		repository.save(Store.getInstance("VNNDK","CTY CP DAU TU BAC KY", "Lô 7, đường TS9, KCN Tiên Sơn, Tiên Du, Bắc Ninh", "Công ty CP đầu tư Bắc Kỳ"));
		repository.save(Store.getInstance("VNNDL","CTYTNHH ALS BAC NINH", "Lô CN05, đường YP6, KCN Yên Phong, Bắc Ninh", "Cty TNHH ALS Bắc Ninh"));
		repository.save(Store.getInstance("VNNDM","CT GNVC INDO-TRAN", "Lô số 66, KCN VSIP Bắc Ninh", "Cty CP giao nhận và vận chuyển INDO Trần"));
		repository.save(Store.getInstance("VNNDN","SAGAWA EXPRESS (HN)", "Lố số 66, KCN VSIP, Bắc Ninh", " Cty TNHH Sagawa Express Việt Nam tại Hà Nội -Từ sơn"));
		repository.save(Store.getInstance("VNNDO","CTLOGISTIC TIN NGHIA", "ICD Biên Hoà, Đồng Nai", "KNQ Công ty CP Logistics Tín Nghĩa"));
		repository.save(Store.getInstance("VNNDP","CTTNHH TV THANG LONG", "Lô 101/1 đường Amata, KCN Amata, Biên Hoà", "KNQ Công ty TNHH Tiếp Vận Thăng Long-CN Đồng Nai."));
		repository.save(Store.getInstance("VNNDQ","ICD T.CANG LONG BINH", "ICD Long Bình - Đồng Nai", "KNQ Công ty CP ICD Tân Cảng - Long Bình."));
		repository.save(Store.getInstance("VNNDR","CONG TY TNHH VOPAK", "KCN Ông Kèo - Phước Khánh – Nhơn Trạch", "Kho ngoại quan Vopak"));
		repository.save(Store.getInstance("VNNDS","CT TNHH CP HOA VIET", "KCN Hố Nai 3, Đồng Nai", "KNQ Công ty CP Hòa Việt "));
		repository.save(Store.getInstance("VNNDT","CT TNHH XUAN CUONG", "Km1+900, QL 51, Long Bình Tân, Biên Hoà, Đồng Nai", "Công ty TNHH Xuân Cường"));
		repository.save(Store.getInstance("VNNDU","CTCP TV THANH LONG", "ICD Tân Cảng, Long Bình, Đồng Nai", "KNQ Công ty CP Tiếp Vận Thành Long "));
		repository.save(Store.getInstance("VNNDV","CTTNHH KCTC VINA", "Lô 28B, KCN Nhơn Trạch 1, Đồng Nai", "47NGW01 - Kho ngoại quan KCTC"));
		repository.save(Store.getInstance("VNITS","ICD TIEN SON BAC NINH", "", ""));
		repository.save(Store.getInstance("VNKCT","KHU KTCK CAU TREO", "", ""));
		repository.save(Store.getInstance("VNKLB","KHU TM LAO BAO", "", ""));
		repository.save(Store.getInstance("VNFBG","CTCPXD GIAO THONG", "", " Công ty CP xây dựng Công trình giao thông và cơ giới."));
		repository.save(Store.getInstance("VNFBH","INLACO LOGISTIC", "", "Chi nhánh công ty hợp tác lao động với nước ngoài phía Nam (Inlaco logistic)"));
		repository.save(Store.getInstance("VNFBI","CFS CT LEN SAI GON", "Lô C9, khu công nghiêp Hòa Xá, TP. Nam Định, tỉnh Nam Định", "Công ty cổ phần kinh doanh len Sài Gòn"));
		repository.save(Store.getInstance("VNFBJ","CT ISHENG ELECTRIC", "", "Công ty TNHH Isheng Electric Wire&Cable Việt Nam"));
		repository.save(Store.getInstance("VNFBK","FUNING PRECISION", "", "Công ty TNHH Funing Precision Component"));
		repository.save(Store.getInstance("VNFBL","CT TENMA VIET NAM", "", "Công ty TNHH Tenma Việt Nam"));
		repository.save(Store.getInstance("VNFBM","CT VS TECHNOLOGY", "", "Công ty TNHH VS Technology"));
		repository.save(Store.getInstance("VNFBN","CT VS INDUSTRY VN", "", "Công ty TNHH VS Industry Việt Nam"));
		repository.save(Store.getInstance("VNFBO","EUNSUNG ELECTRONICS", "", "Công ty Eunsung Electronics Vina"));
		repository.save(Store.getInstance("VNFBP","CT MITAC COMPUTER", "", "Công ty TNHH Mitac Computer (Việt Nam)"));
		repository.save(Store.getInstance("VNFBQ","CT MITAC PRECISION", "", "Công ty TNHH Mitac Precisicon Technology (Việt Nam)"));
		repository.save(Store.getInstance("VNFBR","CT AMSTRONG WESTON", "", "Công ty TNHH Amstrong Weston Việt Nam"));
		repository.save(Store.getInstance("VNFBS","HAYAKAWA ELECTRONICS", "", "Công ty TNHH Hayakawa Electronics Việt Nam (Chi cục HQ Bắc Ninh)"));
		repository.save(Store.getInstance("VNFBT","SAMSUNG ELECTRONICS", "", "Công ty TNHH Samsung Electronics Việt Nam"));
		repository.save(Store.getInstance("VNFBU","CT SHIHEN VIETNAM", "", "Công ty TNHH Shihen Việt Nam"));
		repository.save(Store.getInstance("VNFBV","CT NHUA KINH QUANG", "", "Công ty TNHH nhựa Kinh Quang Việt Nam"));
		repository.save(Store.getInstance("VNFBW","CT MINH TRI", "", "Công ty TNHH Minh Trí"));
		repository.save(Store.getInstance("VNFBY","CT KINYOSHA VN", "", "Công ty TNHH Kinyosha Việt Nam"));
		repository.save(Store.getInstance("VNFBZ","CT WINTEK VIET NAM", "", "Công ty TNHH Wintek Việt Nam"));
		repository.save(Store.getInstance("VNFCA","FUHONG PRECISION", "", "Công ty TNHH Fuhong precision component (Bắc Giang)"));
		repository.save(Store.getInstance("VNFCB","CT DOVAN", "", "Công ty TNHH Dovan"));
		repository.save(Store.getInstance("VNFCC","CT BAO BI HAO NHUE", "", "Công ty TNHH Bao bì Hạo Nhuệ Việt Nam"));
		repository.save(Store.getInstance("VNFCD","CT BIRZ VIET NAM", "", "Công ty TNHH Birz Việt Nam"));
		repository.save(Store.getInstance("VNFCE","CT IN BAO BI SUNNY", "", "Công ty TNHH In bao bì Sunny Việt Nam"));
		repository.save(Store.getInstance("VNFCF","KORETSUNE SEIKO VN", "", "Công ty TNHH KORETSUNE SEIKO Việt Nam"));
		repository.save(Store.getInstance("VNFCG","CT STRONICS VN", "", "Công ty TNHH STRONICS Việt Nam"));
		repository.save(Store.getInstance("VNFCH","CT UMEC VN", "", "Công ty TNHH UMEC Việt Nam"));
		repository.save(Store.getInstance("VNFCI","CT ASEAN LINK", "", "Công ty TNHH ASEAN - Link (Việt Nam)"));
		repository.save(Store.getInstance("VNFCJ","CT DTU SUNG JIN VN", "", "Công ty TNHH điện tử Sung Jin Việt Nam"));
		repository.save(Store.getInstance("VNFCK","CT DAINICHI COLOR", "", "Công ty TNHH Dainichi Color"));
		repository.save(Store.getInstance("VNFCL","SUMITOMO ELECTRIC", "", "Công ty TNHH Sumitomo Electric Interconnect Products Việt Nam"));
		repository.save(Store.getInstance("VNFCM","HAYAKAWA ELECTRONICS", "", "Công ty TNHH Hayakawa Electronics Việt nam (Chi cục HQ ICD Tiên Sơn)"));
		repository.save(Store.getInstance("VNFCN","TABUCHI ELECTRONIC", "", "Công ty TNHH Tabuchi Electronic"));
		repository.save(Store.getInstance("VNFCO","CT TOHO PRECISION", "", "Công ty TNHH Toho Precision Việt Nam"));
		repository.save(Store.getInstance("VNFCP","CTCP SX SONG HONG", "", "Công ty cổ phần sản xuất Sông Hồng"));
		repository.save(Store.getInstance("VNFCQ","CT JOYO MARK VN", "", "Công ty TNHH Joyo Mark (Việt Nam)"));
		repository.save(Store.getInstance("VNFCR","KURABE INDUSTRIAL BN", "", "Công ty TNHH Kurabe Industrial Bắc Ninh"));
		repository.save(Store.getInstance("VNFCS","DAIICHI DENSO BUHIN", "", "Công ty TNHH Daiichi Denso Buhin Việt Nam"));
		repository.save(Store.getInstance("VNFCT","CONG NGHE MUTO HN", "", "Công ty TNHH Công nghệ Muto Hà Nội"));
		repository.save(Store.getInstance("VNFCU","CT CANON VN", "", "Công ty TNHH Canon - Việt Nam"));
		repository.save(Store.getInstance("VNFCV","CT CANON VN QUE VO", "", "Công ty TNHH Canon Việt Nam-CN Quế Võ"));
		repository.save(Store.getInstance("VNFCW","CT CANON VN TIEN SON", "", "Công ty TNHH Canon Việt Nam- CN Tiên Sơn"));
		repository.save(Store.getInstance("VNFCY","CFS ICD TAN CANG LB", "", "Kho CFS ICD Tân cảng -Long Bình "));
		repository.save(Store.getInstance("VNFCZ","CT HANG HAI VIMADECO", "", "Cty cổ phần phát triển hàng hải Vimadeco"));
		repository.save(Store.getInstance("VNNEV","VINA COMMONDITIES", "", "Kho ngoại quan Vina Commondities"));
		repository.save(Store.getInstance("VNNEW","KNQ BARIA SERECE", "", "Kho ngoại quan cảng Baria Serece"));
		repository.save(Store.getInstance("VNNEX","KNQ PTSC", "", "Kho ngoại quan PTSC"));
		repository.save(Store.getInstance("VNNEY","KNQ CANG VAN AN", "", "Kho ngoại quan Cảng Vạn An"));
		repository.save(Store.getInstance("VNNEZ","KNQ CAN THO", "", "Kho ngoại Quan Cần Thơ"));
		repository.save(Store.getInstance("VNNFA","CTCP CANG DONG NAI", "", "KNQ Công ty CP Cảng Đồng Nai"));
		repository.save(Store.getInstance("VNNFB","CT VINH CUONG", "", "KNQ Công ty TNHH Vĩnh Cường"));
		repository.save(Store.getInstance("VNNFC","CT DTU TM DV QTE", "", "Công ty TNHH Đầu tư TM và Dịch vụ quốc tế"));
		repository.save(Store.getInstance("VNNFD","KNQ CT LEN SAI GON", "Lô C9, khu công nghiêp Hòa Xá, TP. Nam Định, tỉnh Nam Định", "Kho ngoại quan của Công ty cổ phần kinh doanh len Sài Gòn"));
		repository.save(Store.getInstance("VNNFE","CT CCI VIET NAM", "", "Công ty TNHH CCI Việt Nam"));
		repository.save(Store.getInstance("VNNFF","CT YUSEN LOGISTICS", "", "Cty TNHH YuSen Logistics Solutions Việt Nam"));
		repository.save(Store.getInstance("VNNFG","KHO NGOAI QUAN HUE", "", "Kho ngoại quan (Huế)"));
		repository.save(Store.getInstance("VNXAA","CT BROTHER VIET NAM", "", ""));
		repository.save(Store.getInstance("VNXAB","CT LEO VIET NAM", "", ""));
		repository.save(Store.getInstance("VNXAC","VINH HAN PRECISION", "", ""));
		repository.save(Store.getInstance("VNXAD","HITACHI CABLE VN", "", ""));
		repository.save(Store.getInstance("VNXAE","ADVANEX VIET NAM", "", ""));
		repository.save(Store.getInstance("VNXAF","CT MATEX VIET NAM", "", ""));
		repository.save(Store.getInstance("VNXAG","PRINCETON BIOMEDITEC", "", ""));
		repository.save(Store.getInstance("VNXAH","CT JAGUAR HA NOI", "", ""));
		repository.save(Store.getInstance("VNXAI","PEGASUS VIET NAM", "", ""));
		repository.save(Store.getInstance("VNXAJ","CT VSM NHAT BAN", "", ""));
		repository.save(Store.getInstance("VNXAK","CT SEIKO VIET NAM", "", ""));
		repository.save(Store.getInstance("VNXAL","SUMIDENSO VIET NAM", "", ""));
		repository.save(Store.getInstance("VNXAM","TOWADA VIET NAM", "", ""));
		repository.save(Store.getInstance("VNXAN","CT AUREOLE BCD", "", ""));
		repository.save(Store.getInstance("VNXAO","VINA OKAMOTO", "", ""));
		repository.save(Store.getInstance("VNXAP","LFV METAL VIET NAM", "", ""));
		repository.save(Store.getInstance("VNXAQ","VALQUA VIET NAM", "", ""));
		repository.save(Store.getInstance("VNXAR","DIEN TU IRISO VN", "", ""));
		repository.save(Store.getInstance("VNXAS","CT UMC VIET NAM", "", ""));
		repository.save(Store.getInstance("VNXAT","DIEN TU TAISEI HANOI", "", ""));
		repository.save(Store.getInstance("VNXAU","AIDEN VIET NAM", "", ""));
		repository.save(Store.getInstance("VNXAV","ATARIH PRECISION VN", "", ""));
		repository.save(Store.getInstance("VNXAW","IKKA VIET NAM", "", ""));
		repository.save(Store.getInstance("VNXAX","NISSEI VIET NAM", "", ""));
		repository.save(Store.getInstance("VNXAY","SUMIDEN VIET NAM", "", ""));
		repository.save(Store.getInstance("VNXAZ","FUJI SEIKO VN", "", ""));
		repository.save(Store.getInstance("VNXBA","CT GESHEN VN", "", ""));
		repository.save(Store.getInstance("VNXBB","HINSITSU SCREEN VN", "", ""));
		repository.save(Store.getInstance("VNXBC","KEFICO VIETNAM", "", ""));
		repository.save(Store.getInstance("VNXBD","CT KIM THUY PHUC", "", ""));
		repository.save(Store.getInstance("VNXBE","KURODA KAGAKU VN", "", ""));
		repository.save(Store.getInstance("VNXBF","MEIJTSU TONGDA VN", "", ""));
		repository.save(Store.getInstance("VNXBG","MIZUHO PRECISION VN", "", ""));
		repository.save(Store.getInstance("VNXBH","NISHOKU TECHNOLOGY", "", ""));
		repository.save(Store.getInstance("VNXBI","CT SANSEI VN", "", ""));
		repository.save(Store.getInstance("VNXBJ","CT SSK VIET NAM", "", ""));
		repository.save(Store.getInstance("VNXBK","TAISHODO VIETNAM", "", ""));
		repository.save(Store.getInstance("VNXBL","CT LIEN DAI VN", "", ""));
		repository.save(Store.getInstance("VNXBM","CT UNIDEN VN", "", ""));
		repository.save(Store.getInstance("VNXBN","SANYU SEIMITSU", "", ""));
		repository.save(Store.getInstance("VNXBO","CT FULUHASHI VN", "", ""));
		repository.save(Store.getInstance("VNXBP","YAZAKI HAI PHONG(TB)", "", ""));
		repository.save(Store.getInstance("VNXBQ","CT SHENGFANG", "", ""));
		repository.save(Store.getInstance("VNXBR","CT TACTICIAN", "", ""));
		repository.save(Store.getInstance("VNXBS","CT QTE DINH LUC", "", ""));
		repository.save(Store.getInstance("VNXBT","MIZUNOPRECISION", "", ""));
		repository.save(Store.getInstance("VNXBU","AKIYAMA SC VN", "", ""));
		repository.save(Store.getInstance("VNXBV","CT SANWA VN", "", ""));
		repository.save(Store.getInstance("VNXBW","CT CORONA VN", "", ""));
		repository.save(Store.getInstance("VNXBX","CT DENYO VN", "", ""));
		repository.save(Store.getInstance("VNXBY","CT CANON VN", "", ""));
		repository.save(Store.getInstance("VNXBZ","DROSSAPHARM VN", "", ""));
		repository.save(Store.getInstance("VNXCA","FANCY CREATION VN", "", ""));
		repository.save(Store.getInstance("VNXCB","CT HAMADEN VN", "", ""));
		repository.save(Store.getInstance("VNXCC","HOYA GLASSDISK VN II", "", ""));
		repository.save(Store.getInstance("VNXCD","CT ICAM VIET NAM", "", ""));
		repository.save(Store.getInstance("VNXCE","CT KOSAKA VN", "", ""));
		repository.save(Store.getInstance("VNXCF","CT KYOCERA VN", "", ""));
		repository.save(Store.getInstance("VNXCG","KYOTO BIKEN HN", "", ""));
		repository.save(Store.getInstance("VNXCH","MG PLASTIC VIET NAM", "", ""));
		repository.save(Store.getInstance("VNXCI","MIKASA VIETNAM", "", ""));
		repository.save(Store.getInstance("VNXCJ","CT VIETNAM MIE", "", ""));
		repository.save(Store.getInstance("VNXCK","CT NIKKISO VN", "", ""));
		repository.save(Store.getInstance("VNXCL","CT OCHIAI VN", "", ""));
		repository.save(Store.getInstance("VNXCM","SEW COMPONENTS VN", "", ""));
		repository.save(Store.getInstance("VNXCN","SHINEI SEIKO VN", "", ""));
		repository.save(Store.getInstance("VNXCO","SHINJO VIETNAM", "", ""));
		repository.save(Store.getInstance("VNXCP","SHOEI VIET NAM", "", ""));
		repository.save(Store.getInstance("VNXCQ","CT SOC VIET NAM", "", ""));
		repository.save(Store.getInstance("VNXCR","CT TBI CN TOYOTA VN", "", ""));
		repository.save(Store.getInstance("VNXCS","CT VLDT SHIN ETSU VN", "", ""));
		repository.save(Store.getInstance("VNXCT","CT VIETINAK", "", ""));
		repository.save(Store.getInstance("VNXCU","CT CN SEICO", "", ""));
		repository.save(Store.getInstance("VNXCV","CT FUJI BAKELITE VN", "", ""));
		repository.save(Store.getInstance("VNXCW","CTCP NIHON LAMY VN", "", ""));
		repository.save(Store.getInstance("VNXCX","CT BRIDGESTONE VN", "", ""));
		repository.save(Store.getInstance("VNXCY","CT BUCHEON VN", "", ""));
		repository.save(Store.getInstance("VNXCZ","CTM CITIZEN VIET NAM", "", ""));
		repository.save(Store.getInstance("VNXEA","NICHIAS HAI PHONG", "", ""));
		repository.save(Store.getInstance("VNXEB","CT GE VIET NAM", "", ""));
		repository.save(Store.getInstance("VNXEC","IIYAMA SEIKI VIETNAM", "", ""));
		repository.save(Store.getInstance("VNXED","CT GIAY KONYA VN", "", ""));
		repository.save(Store.getInstance("VNXEE","CT SIK VIET NAM", "", ""));
		repository.save(Store.getInstance("VNXEF","CT SOUGOU VN", "", ""));
		repository.save(Store.getInstance("VNXEG","CT SUMIDA VN", "", ""));
		repository.save(Store.getInstance("VNXEH","CT SYNZTEC VN", "", ""));
		repository.save(Store.getInstance("VNXEI","TOYODA GOSEI HP", "", ""));
		repository.save(Store.getInstance("VNXEJ","ZHONGXIN YATAI VN", "", ""));
		repository.save(Store.getInstance("VNXEK","HUGE GAIN HOLDINGS", "", ""));
		repository.save(Store.getInstance("VNXEL","CT TIAN JIAO VN", "", ""));
		repository.save(Store.getInstance("VNXEM","CT WANLI VN", "", ""));
		repository.save(Store.getInstance("VNXEN","FUJI XEROX HAI PHONG", "", ""));
		repository.save(Store.getInstance("VNXEO","GERBERA PRECISION VN", "", ""));
		repository.save(Store.getInstance("VNXEP","HUAFENG PLASTIC", "", ""));
		repository.save(Store.getInstance("VNXEQ","IKO THOMPSON VN", "", ""));
		repository.save(Store.getInstance("VNXER","CT INSTANA VN", "", ""));
		repository.save(Store.getInstance("VNXES","CT KOKUYO VN", "", ""));
		repository.save(Store.getInstance("VNXET","NAKASHIMA VIET NAM", "", ""));
		repository.save(Store.getInstance("VNXEU","CT NISSEI ECO VN", "", ""));
		repository.save(Store.getInstance("VNXEV","TOHOKU PIONEER VN", "", ""));
		repository.save(Store.getInstance("VNXEW","YANAGAWA SEIKO VN", "", ""));
		repository.save(Store.getInstance("VNXEX","CT CHENG V", "", ""));
		repository.save(Store.getInstance("VNXEY","FUJI MOLD VIET NAM", "", ""));
		repository.save(Store.getInstance("VNXEZ","TAKAHATA PRECISION", "", ""));
		repository.save(Store.getInstance("VNXFA","CT PV HAI PHONG", "", ""));
		repository.save(Store.getInstance("VNXFB","YAZAKI HAI PHONG VN", "", ""));
		repository.save(Store.getInstance("VNXFC","CT CN NISHIMA VN", "", ""));
		repository.save(Store.getInstance("VNXFD","CT RAYHO VIETNAM", "", ""));
		repository.save(Store.getInstance("VNXFE","CT FONGHO", "", ""));
		repository.save(Store.getInstance("VNXFF","CHUNG YANG FOODS VN", "", ""));
		repository.save(Store.getInstance("VNXFG","CT PTCN BECKEN VN", "", ""));
		repository.save(Store.getInstance("VNXFH","CT KYOCERA VIET NAM", "", ""));
		repository.save(Store.getInstance("VNXFI","CT KYORITSU VN", "", ""));
		repository.save(Store.getInstance("VNXFJ","CT DTU SONG HAO", "", ""));
		repository.save(Store.getInstance("VNXQL","SUNCALL TECHNOLOGY", "", ""));
		repository.save(Store.getInstance("VNDGL","DONG DANG (LANG SON)", "", ""));
		repository.save(Store.getInstance("VNLCL","LAO CAI", "", ""));
		repository.save(Store.getInstance("VNYVH","YEN VIEN (HA NOI)", "", ""));
		repository.save(Store.getInstance("VNFAB","PT HANG HAI HN", "Số 41 Nguyễn Văn Linh, Phường Phúc Đồng, Long Biên, HN", "CN Công ty Phát triển hàng hải tại Hà Nội"));
		repository.save(Store.getInstance("VNFAC","DLVT SAFI HA NOI", " Khu CN vừa và nhỏ xã Phú Thị, Gia Lâm, HN", "CN Công ty Cổ phần Đại lý vận tải Safi"));
		repository.save(Store.getInstance("VNFAD","ICD TRANSIMEX SG", "", "Công ty CP ICD Transimex Sài Gòn"));
		repository.save(Store.getInstance("VNFAE","TNHH CANG PHUOC LONG", "", "Công ty TNHH cảng Phước Long"));
		repository.save(Store.getInstance("VNFAF","TNHH XNK TAY NAM", "Km 9 Xa lộ Hà Nội, Trường Thọ, Thủ Đức, TP. HCM", "Công ty TNHH MTV SXTM XNK Tây nam"));
		repository.save(Store.getInstance("VNFAG","CP TM XNK DONG TAY", "Lô G2 đường K1, cụm 2, KCN Cát Lái, Quận 2 – TP. HCM", "Công ty CP TM NNK Đông Tây"));
		repository.save(Store.getInstance("VNFAH","CFS SAFI HO CHI MINH", "06 đường Đào Trí, Quận 7 – TP. HCM", "CFS SAFI"));
		repository.save(Store.getInstance("VNFAI","KHO VAN BINH MINH", "27B Trường Sơn, Phường Linh Xuân, Q. Thủ Đức, HCM", "Công ty TNHH Liên doanh Giao nhận Kho vận Bình Minh"));
		repository.save(Store.getInstance("VNFAJ","SAGAWA EXPRESS VN", "Tổ 4, Phường Linh Xuân, Q. Thủ Đức, HCM", "Công ty TNHH Sagawa Express Việt Nam"));
		repository.save(Store.getInstance("VNFAK","TIEP VAN SO 1", "Cảng VICT", "Công ty LD tiếp vận số 1"));
		repository.save(Store.getInstance("VNFAL","DICH VU HANG HAI SG", "", "Cty CP dịch vụ hàng hải Sài Gòn SCSC"));
		repository.save(Store.getInstance("VNFAM","DV HH VINALINES HP", "Khu CN Đình Vũ, P. Đông Hải 2, Hải An, HP", "Công ty dịch vụ hàng hải VINALINES Hải Phòng "));
		repository.save(Store.getInstance("VNFAN","QUOC TE NHAT VIET", "Khu vực Chùa vẽ, HP", "Công ty vận tải quốc tế Nhật - Việt"));
		repository.save(Store.getInstance("VNFAO","CONTAINER VIETNAM", "Số 1 Đường Ngô Quyền, HP", "Công ty cổ phần container Việt Nam"));
		repository.save(Store.getInstance("VNFAP","GEMADEPT HP", "Km1 Đường bao Trần Hưng đạo, Hải An, HP", "Công ty TNHH một thành viên GEMADEPT "));
		repository.save(Store.getInstance("VNFAQ","LDKHAITHAC CONTAINER", "309 Ngô Quyền, TP. Hải Phòng", "Công ty LD khai thác container Việt Nam"));
		repository.save(Store.getInstance("VNFAR","CONTAINER MINH THANH", "190 Đường Đình Vũ, Đông Hải 2, Hải An, HP", "Công ty TNHH container Minh Thành"));
		repository.save(Store.getInstance("VNFAS","TIEP VAN NAM PHAT", "Km 103 đường bào Nguyễn Bỉnh Khiêm, Đông Hải, Hải An, HP", "Công ty TNHH tiếp vận Nam Phát"));
		repository.save(Store.getInstance("VNFAT","QUOC TE SAO DO", "2 đường bao TRần Hưng Đạo, Đông Hải, Hải An, HP", "Chi nhánh công ty TNHH quốc tế Sao Đỏ"));
		repository.save(Store.getInstance("VNFAU","HANG HAI VN PHIA BAC", "Km 105 đường Nguyễn Bỉnh Khiêm, Đông Hải 2, Hải An, HP", "CN Công ty CP Hàng hải Việt Nam - Đại lý vận tải quốc tế phía Bắc"));
		repository.save(Store.getInstance("VNFAV","VAN TAI DUYEN HAI", "Km104 đường Nguyễn Bỉnh Khiêm, Đông Hải 2, Hải An, HP", "Công ty TNHH vận tải Duyên Hải"));
		repository.save(Store.getInstance("VNFAW","VAN TAI THUE TAU", "Phường Đông Hải, Hải An, HP", "Chi nhánh Công ty vận tải và thuê tàu tại Hải Phòng"));
		repository.save(Store.getInstance("VNFAX","TAN TIEN PHONG", "Đường K9 Đông Hải 2 Hải An Hải Phòng", "Công ty TNHH Tân Tiên Phong"));
		repository.save(Store.getInstance("VNFAY","VANTAI CONG NGHE CAO", "Cảng Transvina Đường Ngô Quyền Hải Phòng", "Công ty TNHH vận tải hàng công nghệ cao"));
		repository.save(Store.getInstance("VNFAZ","LOGISTICS XANH", "Lô đất CN3.2G, Khu công nghiệp Đình Vũ, phường Đông Hải 2, quận Hải An, thành phố Hải Phòng", "Công ty TNHH một thành viên  Trung tâm Logistic Xanh "));
		repository.save(Store.getInstance("VNFBA","NAM LIEN", "Lô J2 đường số 9, KCN Sóng Thần 1. Dĩ An, Bình Dương", "Kho Nam Liên"));
		repository.save(Store.getInstance("VNFBB","TM DL BINH DUONG", "Số 9 đường DT743, xã Bình Thắng, huyện Dĩ An, tỉnh Bình Dương", "Công ty cổ phần thương mại và du lịch Bình Dương"));
		repository.save(Store.getInstance("VNFBC","ICD TANCANG SONGTHAN", "DT-743 xã Bình Hòa, huyện Thuận An, tỉnh Bình Dương", "Công ty TNHH MTV ICD Tân Cảng Sóng Thần thuộc tổng công ty Tân Cảng – Sóng Thần"));
		repository.save(Store.getInstance("VNFBD","TAN HOAN CAU", "Khu CN Sóng Thần - Bình dương", "Công ty Tân Hoàn cầu"));
		repository.save(Store.getInstance("VNFBE","CANG LS", "KCN Hòa Xá, Nam Định", "Công ty TNHH cảng LS"));
		repository.save(Store.getInstance("VNFBF","DAU TU BAC KY", "KCN Tiên Sơn, Bắc Ninh", "Cty CP đầu tư Bắc Kỳ"));
		repository.save(Store.getInstance("VNNAA","GN KV NGOAI THUONG", "Số 142, Lê Lai, HP", "Công ty CP giao nhận kho vận ngoại.thương Hải Phòng (Vietrans HP)"));
		repository.save(Store.getInstance("VNNAB","TM DV XNK HAI PHONG", "Số 3 Ngô Quyền, HP", "Công ty CP thương mại, dịch vụ và XNK Hải Phòng                  (Tradimexco HP)"));
		repository.save(Store.getInstance("VNNAC","TM XD 5 HP", "Số 6, Nguyễn Trãi, HP", "Công ty CP thương mại xây dựng 5 Hải Phòng - Vietracimex - HP)"));
		repository.save(Store.getInstance("VNNAD","TNHH THANH HUYEN", "750 Nguyễn Văn Linh, Lê Chân, HP", "Công ty TNHH Thanh Huyền"));
		repository.save(Store.getInstance("VNNAE","TM HOANG CAU", "Km2, đường đi Đảo Đình Vũ, Đông Hải, Hải An, HP", "Công ty cổ phần thương mại Hoàng Cầu"));
		repository.save(Store.getInstance("VNNAF","VAT LIEU DIEN HP", "Số 384, Lê Thánh Tông, HP", "Công ty CP hóa chất Vật Liệu Điện Hải Phòng (Cemaco HP)"));
		repository.save(Store.getInstance("VNNAG","CN LH VAN CHUYEN HP", "Đường Trần Hưng Đạo, Hải An, HP", "Chi nhánh công ty CP Đại lý liên hiệp vận chuyển tại Hải Phòng (GEMADEPT HP)"));
		repository.save(Store.getInstance("VNNAH","VIJACO", "Lô E0-3B KCN Nomura, HP", "Công ty vận tải quôc tế Nhật - Việt	(Vijaco)"));
		repository.save(Store.getInstance("VNNAI","XNK QUANG BINH", "Lô CN4.4F và CN4.4G, khu công nghiệp Đình Vũ, phường Đông Hải 2, quận Hải An thuộc khu kinh tế, Đình Vũ - Cát Hải, Tp. Hải Phòng", "Công ty cổ phần XNK Quảng Bình"));
		repository.save(Store.getInstance("VNNAJ","BINH PHU", "89 Lê Thánh Tông, Đông Hải 1, Hải An, HP", "Công ty TNHH Bình Phú"));
		repository.save(Store.getInstance("VNNAK","CAT VAN", "Bãi VN Logistics, phường Đông Hải 2, quận Hải An, HP", "Công ty TNHH thương mại Cát Vận"));
		repository.save(Store.getInstance("VNNAL","GIAONHAN NT HAIPHONG", "Đông Hải 2, Hải An, Hải Phòng", "Công ty CP Giao nhận vận tải ngoại thương tại Hải phòng"));
		repository.save(Store.getInstance("VNNAM","KNQ LOGISTICS XANH", "Đông Hải 2, Hải An, HP", "Công ty TNHH MTV Trung tâm Logistics Xanh"));
		repository.save(Store.getInstance("VNNAN","VINALINE LOGISTIC VN", "Lê Thánh Tông, Ngô Quyền, HP", "Công ty cổ phần VINALINES Logistics Việt Nam"));
		repository.save(Store.getInstance("VNNAO","KNQ CTY CP AB PLUS", "Đông Hải 2, Hải An, HP", "Công ty cổ phần Hùng Cường"));
		repository.save(Store.getInstance("VNNAP","MASCOT VN", "Lô CN3.1, KCN Tân Trường, huyện Cẩm Giàng, Hải Dương.", "Công ty TNHH Mascot Việt Nam"));
		repository.save(Store.getInstance("VNNAQ","VAN TAI VIET NHAT HD", "Lô C4, KCN Phúc Điền, huyện Cẩm Giàng, Hải Dương.", "Công ty TNHH Vận tải Việt Nhật Chi nhánh Hải Dương"));
		repository.save(Store.getInstance("VNNAR","SANKYULOGISTICS HD", "KCN Tân Trường, huyện Cẩm Giàng, Hải Dương", "Chi nhánh công ty TNHH Sankyu Logistics VN tại Hải Dương"));
	}
}
