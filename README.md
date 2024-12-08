# A smart printing service for students at HCMUT

**LỚP:** L01  
**NHÓM:** SwiftPrint

**GV:** Trương Trần Tuấn Phát

**Sinh viên thực hiện:**

| Tên               | MSSV    | % đóng góp |
|-------------------|---------|------------|
| Bùi Thanh Tùng    | 2213860 | 100%       |
| Đoàn Viết Tường   | 2213882 | 100%       |
| Huỳnh Ngọc Minh	  | 2212036 | 100%       |
| Đoàn Nhật Tiến	  | 2213449 | 100%       |
| Huỳnh Minh Trọng  | 2213671 | 70%        |
| Lương Thanh Tùng	| 2213867 | 50%        |
| Trần Uy	          | 2213897 | 100%       |
| Trần Đình Tưởng   | 2213892 | 100%       |

---
## TASK 1: REQUIREMENT ELICITATION

### 1.1 Describe the domain context of a smart printing service for students at HCMUT. Who are relevant stakeholders? What are their current needs? In your opinion, what benefits HCMUT-SSPS will be for each stakeholder?
#### 1.1.1 Bối cảnh của dự án
Trong thời đại ngày này, giáo dục là quốc sách hàng đầu cho sự phát triển của đất nước. Trong đó, để có thể học tập một cách hiệu quả, thì các tài liệu luôn là những nhân tố không thể thiếu đối với mỗi học sinh, sinh viên. Vì vậy, nhu cầu in tài liệu để học tập đang ngày một tăng lên. 

- Đa phần sinh viên không biết chỗ in tài liệu trong trường khi muốn in tài liệu thường di chuyển khá xa
- Thông tin cá nhân cũng như tài liệu in ấn không được bảo mất 
- Thời gian chờ đợi lâu do cửa hàng bên ngoài phục vụ khách hàng nhiều trường
- Phải gửi tài liệu qua các ứng dụng không chuyên dụng Messenger, Zalo không thuận tiện quản lý, tiềm ấn gây ra lỗi file 
- Không công khai giá xảy ra vượt chi phí khách hàng
- Cơ sở in ấn điều chỉnh file in không đúng mong muốn của bạn
- Bên quản lý in ấn thường khó thống kê, quản lý máy in không hiệu quả

Phần mềm dịch vụ in ấn danh cho sinh viên tại Trường Đại học Bách Khoa TP.HCM (HCMUT_SSPS) sẽ giải quyết các vấn đề một cách đơn giản, hiệu quả: 
- Các địa điểm máy in hiển thị trên hệ thống thuận tiện cho việc in 
- Xác thực SSO trước khi sử dụng, thông tin cá nhân của bạn cũng như tài liệu in được bảo mật
- Bạn có thể in trực tiếp tại máy hoặc đặt lịch nhận 
- Dịch vụ chỉ phục vụ sinh viên trường nên không chờ đợi quá lâu
- Tránh lỗi file khi tải lên, điều chỉnh thuôc tính in theo mong muốn 
- Số tiền thành toán hiển thị rõ trên hệ thống, còn giúp bạn quản lý chi phí qua lịch sử giao dịch
#### 1.1.2 Các bên liên quan (stakeholders)
Các bên liên quan có liên quan của hệ thống bao gồm:

- **Khách hàng (sinh viên và giảng viên):** khách hàng cần một dịch vụ in ấn thuận tiện và hiệu quả trong khuôn viên trường. Đồng thời cần có thể in tài liệu của mình một cách nhanh chóng và dễ dàng, cũng như cần có thể theo dõi lịch sử in ấn của mình.Tài liệu khi được in ra cần được đảm bảo chính xác nhất có thể. In ấn an toàn và bảo mật thông tin
- **Cán bộ Quản lý Dịch vụ In ấn Sinh viên (SPSO):** SPSO chịu trách nhiệm quản lý dịch vụ in ấn và đảm bảo nó hoạt động trơn tru. Họ cần các công cụ để giám sát và kiểm soát khả dụng máy in, theo dõi lịch sử in ấn và cấu hình cài đặt hệ thống.

- **Đơn vị xác thực HCMUT_SSO (hệ thống xác thực sinh viên Bách Khoa):** Để đảm bảo quyền lợi, tính bảo mật cho sinh viên Bách Khoa, hệ thống xác thực cần phải đảm bảo rằng chỉ khi có tài khoản được xác thực trong nội bộ Trường Đại học Bách Khoa TP.HCM mới được sử dụng phần mềm. 

- **Đơn vị quản lý thanh toán BKPay:** Mỗi kì được nhà trường cấp một số giấy nhất định nhưng khi sinh viên muốn in tài liệu nhiều hơn thì có thể mua thêm giấy , thanh toán qua hệ thống đơn giản và bảo mật.

#### 1.1.3 Lợi ích của các bên liên quan tới hệ thống
Một số lợi ích có thể có của các bên liên quan về hệ thống này:

- **Khách hàng (sinh viên và giảng viên):** In tài liệu một cách nhanh chóng và dễ dàng, không cần phải xếp hàng dài chờ đợi. Khách hàng có thể theo dõi lịch sử in ấn của mình để quản lý chi phí. In tài liệu từ bất kỳ máy in nào trong khuôn viên trường, thuận tiện. Khách hàng không cần lo lắng về tính bảo mật của thông tin cá nhân và tài liệu.

- **Cán bộ Quản lý Dịch vụ In ấn Sinh viên (SPSO):** Dễ dàng giám sát và quản lý máy in. Quản lý các cài đặt hệ thống một cách dễ dàng và tiện lợi. Có thể tạo báo cáo về việc sử dụng in ấn và chi phí để hỗ trợ ra quyết định.

- **Đơn vị quản lý thanh toán BKPay:**
Thông kê tính toán hóa đơn nhanh chóng, dễ dàng.

- **Đơn vị xác thực HCMUT_SSO (hệ thống xác thực sinh viên Bách Khoa):**
Dễ dàng quản lý và đảm bảo quyền lợi.

### 1.2 Describe all functional and non-functional requirements that can be inferred from the project description. |


Non-function khác :
- Tính có sẵn :
  - Hệ thống có sẵn 24h nhưng nhận tài liệu in trực tiếp thì 7 am đến 9 pm.
  - Bảo trì thì chỉ phòng kỹ thuật mới đăng nhập được.
- Hiệu suất:
  - Đảm bảo chịu tải 1000 luợt truy cập cùng lúc

### 1.3 Draw a use-case diagram for the whole system. Choose an important module and draw its use-case diagram, as well as describe the use-case using a table format	

#### 1.3.1 Quản lý máy in
 
**Xem thông tin máy in**

**Thêm máy in**

**Bật/ Tắt máy in**

#### 1.3.2 In tài liệu
 
**In tài liệu**

**Tải tài liệu**

## SYSTEM MODELLING
### 2.1  Draw an activity diagram to capture the business process between systems and the stakeholders in a particular module (choose a module in Task 1.3)
 
- Sinh viên truy cập vào trang chủ của hệ thống và bắt đầu đăng nhập.
- Sau khi đăng nhập thành công, sinh viên có thể thực hiện upload file cần in ấn lên hệ thống.
- Hệ thống sẽ kiểm tra xem file mà sinh viên gửi có format hợp lệ không.
  - Nếu hợp lệ, sinh viên được đi tới bước tiếp theo và được phép chọn thuộc tính về in ấn (số tờ,
các mặt, loại tờ,...)
  - Nếu không hợp lệ, sinh viên cần phải upload lại file cần in.
- Sau khi sinh viên chọn máy in mong muốn được in tài liệu, SPSO sẽ kiểm tra tình trạng của máy in này.
  - Nếu máy in có thể hoạt động tốt, SPSO sẽ thêm vào lịch sử các tài liệu cần in.
  - Trường hợp còn lại khi máy in sinh viên yêu cầu có vấn đề, SPSO sẽ yêu cầu sinh viên chọn
lại máy in.
- Sau khi sinh viên chọn các thuộc tính, hệ thống sẽ check lại số lượng giấy mà sinh viên được in không
cần trả thêm.
  - Nếu số lượng giấy sinh viên cần in chưa vượt quá số lượng được in không trả thêm còn lại, hệ thống cho phép sinh viên được chọn máy in mà sinh viên đó mong muốn in, đồng thời sẽ trừ
đi lượng giấy in của sinh viên.
  - Trường hợp còn lại, hệ thống sẽ tính toán lượng cần trả thêm. Sau đó hệ thống sẽ gửi cho sinh viên hóa đơn. Sinh viên trả tiền sẽ được BKPay xác nhận giao dịch và đi tới bước chọn máy in.
- Sau khi SPSO update lại history, máy in được chọn sẽ thêm yêu cầu vào queue và sẽ tự động in tài liệu.
- Cuối cùng, sinh viên tới máy in mà sinh viên mong muốn in để nhận tài liệu.
### 2.2 Draw a sequence diagram for a particular module (the same with the module used in task 2.1)
#### 2.2.1 Quản lý máy in
**Xem thông tin máy in**
 
Mô tả:
- SPSO thực hiện việc xem thông tin máy in
- SPSO vào trang Quản lý máy in
- SPSO nhập tên máy in hoặc chọn từ danh sách có sẵn phía dưới màn hình.
- Hệ thống sẽ xác định tên máy in và truy vấn thông tin máy in từ cơ sở dữ liệu.
- Sau khi truy vấn thành công sẽ hiển thị thông tin máy in trên trang Chi tiết máy in cho SPSO.

**Thêm máy in**
 
Mô tả:
- SPSO thực hiện việc thêm máy in
- SPSO vào trang Quản lý máy in
- SPSO nhập tên máy in muốn thêm.
- Hệ thống sẽ xác định tên máy in và truy vấn thông tin máy in từ cơ sở dữ liệu. Sẽ có 2 trường hợp: 
  - Đã có máy in có tên đó, hệ thống sẽ xuất thông báo “ Máy in đã có trong hệ thống “
  - Chưa có máy in có tên đã nhập, hệ thống sẽ yêu cầu nhập thông tin máy in. SPSO 	   nhập thông tin máy in và hệ thống sẽ thêm máy in vào trong database.

**Bật tắt máy in**

 
Mô tả:
- SPSO thực hiện việc bật / tắt máy in
- SPSO vào trang Quản lý máy in
- SPSO nhập tên máy in muốn thap tác.
- Hệ thống sẽ xác định tên máy in và truy vấn thông tin máy in từ cơ sở dữ liệu. Sẽ có 2 trường hợp: 
  - Không có máy in có tên đó, hệ thống sẽ xuất thông báo “ Không có máy in yêu cầu trong hệ thống “ 
  - Có máy in có tên đã nhập, SPSO thực hiện thao tác tắt / bật máy in. Máy in sẽ tắt/ bật theo yêu cầu.

#### 2.2.2 In tài liệu
 
Mô tả:
- Sinh viên bắt đầu thực hiện việc in tài liệu
- Hệ thống tiếp nhận yêu cầu và chuyển hướng đến trang in tài liệu.
- Sinh viên sau đó tiến hành các bước sau đây.
  - Sinh viên tiến hành tải tài liệu cần in lên hệ thống, hệ thống sẽ kiểm tra định dạng của tài
liệu. Nếu tài liệu có định dạng sai so với quy định thì hệ thống sẽ thông báo lỗi và yêu cầu
sinh viên tải lại tài liệu khác.
  - Sau đó, sinh viên tiến hành chọn máy in. Hệ thống sẽ hiển thị danh sách thông tin các máy
in sẵn có và sinh viên sẽ chọn máy in phù hợp để in tài liệu.
  - Cuối cùng, sinh viên sẽ điều chỉnh các thuộc tính in cho phù hợp với yêu cầu của mình. Trong trường hợp sinh viên không đủ số lượng giấy hiện có trong tài khoản, hệ thống sẽ thông báo lỗi và chuyển hướng qua BKPay để sinh viên thực hiện mua thêm giấy in.
- Sau khi hoàn thành các bước trên, sinh viên sẽ tiến hành in tài liệu.
- Hệ thống sẽ thông báo kết quả và lưu vào lịch sử in.

### 2.3 Draw a class diagram of a particular module (the same with the module used in task 2.1) as comprehensive as possible
#### 2.3.1. Quản lý máy in
 
- SPSO thực hiện các chức năng thêm/bật/tắt máy in thông qua giao diện "Quản lý máy in".
- PrinterManagementSystem là nơi lưu trữ danh sách các máy in có trong hệ thống.
- Với nhánh chức năng bật/tắt máy in, SPSO sẽ chọn trực tiếp máy in đang hiển thị trên giao diện hoặc tìm kiếm thông qua ID, sau đó gửi yêu cầu tới hệ thống. Hệ thống sẽ tiếp nhận các yêu cầu và thực hiện các chức năng enablePrinter(), disablePrinter() trên máy in đã được chọn
- Với nhánh chức năng thêm máy in, sau khi nhận được yêu cầu từ SPSO PrinterManagementSystem sẽ tìm ID của máy in mới được yêu cầu thêm vào, nếu đã tồn tại, hệ thống sẽ gửi thông báo cho SPSO biết là đã có máy in này trong hệ thống, nếu không tìm thấy ID hệ thống sẽ thực hiện chức năng addPrinter()
#### 2.3.2. In tài liệu
 
- Sau khi chọn in tài liệu thì ở phần PrintPage, sinh viên có thể thao tác Upload(tải tài liệu), 
ChoosePrinter(chọn máy in) và ChangeProp(chỉnh sửa các thuộc tính in) 
- PrintController sẽ là nơi xử lí các yêu cầu logic bao gồm RequestPrinterInfo(yêu cầu thông tin máy in), CheckFileType(kiểm tra định dạng file) và CheckProp(kiểm tra các thuộc tính in) 
- Ở phần PrintModel sẽ lưu trữ ID, danh sách các file tài liệu, danh sách các máy in (được lấy từ method GetPrinterInfo của Database). Đồng thời có thể cập nhật các danh sách tài liệu, máy in, ... thông qua PrintModel. 
- Từ PrintModel chúng ta sẽ sinh ra 2 class con là printerModel và fileModel: 
  - printerModel sẽ lưu trữ thông tin của các máy in bao gồm ID, nhãn hiệu, địa điểm, thông tin 
và trạng thái của máy in. Ở đây chúng ta có thể thêm, lấy hoặc set trạng thái cho các máy in.
  - fileModel sẽ lưu trữ thông tin của các file tài liệu được tải lên bao gồm ID, định dạng tài liệu, tên, số trang, đã được kiểm tra định dạng hay chưa. Đồng thời chúng ta cũng có thể thêm, 
lấy hoặc set trạng thái cho các tài liệu này. 
- Trong trường hợp sinh viên hết số lượng giấy trong tài khoản, hệ thống sẽ chuyển hướng sang BKPay và tại đây sinh viên có thể thực hiện mua thêm giấy và sử dụng method MakePayment để thanh toán trên BKPay. 
- Sau khi thực hiện hết tất cả các bước trên, PrintController sẽ gửi yêu cầu đến Database để thay đổi trạng thái in PrintingStatus và đưa ra thông báo trên màn hình.

### 2.4 Develop MVP 1 as user interfaces of either a Desktop-view central dash board for a particular module (the same with the module used in task 2.1). Decide yourself what to include in the view. Use a wireframe tool like Figma or Adobe XD, or Illustrator
#### 2.4.1 Default and login page
- Người dùng vào ứng dụng rồi chọn đăng
nhập tài khoản sinh viên hoặc tài khoản
quản lý SPSO.

#### 2.4.2 User Login and Home page
- Người đùng đăng nhập với tư cách sinh
viên hoặc admin.

#### 2.4.3 Student upload file page

- Sinh viên tải tài liệu bằng cách bấm nút
tải tài liệu.
- Nếu định dạng tài liệu hợp lệ, bên cạnh tài
liệu sẽ hiển thị nút in. Nếu không hợp lệ,
tài liệu sẽ không thể in.
- Sinh viên bấm nút "In"ở sau tài liệu cần
in, sau đó bấm nút "IN NGAY"để gửi yêu
cầu in tài liệu

#### 2.4.4 Student buy paper page

- Sau khi sinh viên xác nhận in tài liệu, nếu
tài khoản sinh viên không đủ giấy hoặc
không còn giấy, sinh viên cần phải mua
thêm.
- Sinh viên chọn số lượng giấy.
- Sinh viên bấm nút mua, sau đó bấm nút
xác nhận để mua, hoặc bấm nút hủy bỏ để
hoàn tác.

#### 2.4.5.User information

- Sinh viên xem và chỉnh sửa thông tin tài
khoản của mình.
- Hoặc admin xem và chỉnh sửa thông tin tài
khoản của students


#### 2.4.6. Admin manage printer

- Quản trị viên có thể tìm kiếm, thêm, chỉnh
sửa hoặc xóa máy in.

#### 2.4.7 Admin handle requests
- Quản trị viên xác nhận in file cho sinh viên.

#### 2.4.8 Admin settings
- Quản trị viên chỉnh sửa cấu hình và lưu.
## 3 ARCHITECTURE DESIGN
### 3.1 ARCHITECTURAL PATTERNS 

**Tại sao nên dùng MVVM với Clean Architecture:**
- MVVM đã có sự tách biệt về Business logic nhưng nó vẫn phù hợp với các ứng dụng nhỏ nhưng khi ứng dụng của dần to lên các viewmodel sẽ bị phì to dẫn đến việc phân chi nhiệm các khó khăn. Kết hợp với Clean Architecture một cách tiếp cận mạnh mẽ để xây dựng ứng dụng có tính tổ chức cao, dễ bảo trì và dễ mở rộng.
- **Clean Architecture** là một kiến trúc tập trung vào nguyên tắc phân tách trách nhiệm và độc lập giữa các tầng. Nó được thiết kế bởi Robert C. Martin (Uncle Bob).

**Lợi ích khi kết hợp sữ dụng Clean Architecture:**
- Độc lập tầng: Các tầng không phụ thuộc trực tiếp vào nhau, giúp thay đổi tầng này mà không ảnh hưởng tầng khác giao tiếp thông qua Interface
- Tái sử dụng dễ dàng: ObjectModel có thể tái sử dụng trong các ứng dụng khác

**Presentation Layer:**
- **View** là giao diện người dùng, nơi hiển thị dữ liệu và xử lý các tương tác của người dùng.
  - Student view: Chế độ mặc định khi truy cập vào trang chính của hệ thống. Dành cho sinh viên và cho phép họ thực hiện việc in ấn tài liệu khi có nhu cầu.
  - SPSO view: Chế độ dành cho SPSO. Cho phép quản lý hệ thống thêm, bớt máy in; thực hiện duyệt các tài liệu được yêu cầu in.
  - Chức năng chính:
    - Nhận dữ liệu từ ViewModel và hiển thị trên UI (giao diện người dùng).
    - Theo dõi các thay đổi trong ViewModel (sử dụng LiveData, StateFlow hoặc các cơ chế tương tự) và cập nhật UI ngay lập tức.
    - Gửi các tương tác của người dùng (như nhấn nút, nhập liệu) đến ViewModel để xử lý
- **View Model:** là lớp trung gian giữa View và Model, đảm bảo rằng View có thể nhận dữ liệu một cách liên tục mà không phụ thuộc trực tiếp vào Model. 
  - file view model : quản lý file từ tệp tin của người dùng từ file 	repository và cung cấp cho view
  - history view model : quản lý lịch sữ in ấn từ history repository và 	cung cấp cho view
  - printer view model : quản lý máy in từ file repository và cung cấp cho 	view
  - transation view model : quán lý các lưu lạil lịch sữ giao dịch của 	người dùng
  - authentication : gọi đến HCMUT SSO để xác thực người dùng và 	phần quyền
  - payment : dùng hệ BK Pay để thực hiện việc thành toán trong việc mua 	giấy
  - Chức năng chính:
    - Chứa dữ liệu cần thiết cho UI và duy trì dữ liệu ngay cả khi cấu hình thay đổi (chẳng hạn khi xoay màn hình).
    - Chuyển đổi dữ liệu từ Model thành dạng mà View có thể sử dụng, chẳng hạn như định dạng lại dữ liệu hoặc lọc dữ liệu.

**Domain Layer:**
- **Interface repository** (không chứa logic truy vấn cụ thể) trong ứng dụng có file 	repository , printer repository, history repository, history repository
- **Domain Model:** file model, printer model , hisotry model là các class object mà bạn sữ dụng để tao tác logic

**Data Layer:**
- **Repositry Implementation:** nơi thực hiện logic về xử lý dữ liệu từ database
- file repository implement : thực hiện các thao tác dữ liệu về file từ 	một cơ sở dữ liệu
  - printer repository implement : thực hiện các thao tác dữ liệu về printer 	(máy in) từ một cơ sở dữ liệu 
  - history repository implement : thực hiện các thao tác dữ liệu về lịch sữ 	in ấn của khách hàng từ một cơ sở dữ liệu 
  - transactions repository implement : thực hiện việc các thao tác về dữ 	liệu giao dịch từ cơ sở dữ liệu

- **Model DTO:**  là nơi lưu trữ và quản lý dữ liệu cũng như logic nghiệp vụ. Đây là lớp chịu trách nhiệm cung cấp dữ liệu từ cơ sở dữ liệu, API hoặc các nguồn khác cho ứng dụng
   - file model, printer model , hisotry model là các class object dùng 		húng dự liệu từ cơ sở dữ liệu   
- Chức năng chính:
  - Đại diện cho dữ liệu của ứng dụng, thường bao gồm các lớp mô hình (data classes).
  - Định nghĩa và áp dụng các quy tắc nghiệp vụ (business rules) như tính toán hoặc xử lý dữ liệu.
  - Kết nối với cơ sở dữ liệu hoặc API thông qua các lớp Repository (các lớp trung gian quản lý truy xuất dữ liệu)
  - Sử dụng Retrofit (thư viên) http request cho việc gọi API, với cơ chế retry và xử lý lỗi mạng để đảm bảo độ tin cậy.

**Remote data source:** với dùng supabase (postgresql) dùng làm cơ sở dụng liệu chính để lưu toàn bộ thông tin của ứng dụng 

#### 3.1.2 Deployment Diagram
 
### 3.2. Component diagram

Hệ thống sẽ có 3 component lớn
- Student Interface component
  - Gồm các component: Printer List(Show các máy in cho người dùng chọn), File uploaded List(Show các file đã được tải lên) và Change properties(Cửa sổ thay đổi chi tiết của việc in)
  - Các component này yêu cầu interface từ PrintViewModel để thực hiện lấy thông tin cần thiết.
  - Component này cũng yêu cầu interface từ đơn vị ngoài(BKPAY) nếu cần thiết cho việc thanh toán
- PrintViewModel component:
  - Chứa các component: PrinterViewModel(Quản lí các hoạt động của máy in), PropertiesViewModel(Quản lí các thay đổi của các chi tiết), FileViewModel(Quản lí các hoạt động của tập tin), và Printing Verifier(Xác minh việc in)
  - 3 component PrinterViewModel, Properties ViewModel, File ViewModel sẽ yêu cầu interface của Printing Verifier trước khi gửi lại cho các component của Student Interface
  - PrintViewModel  sẽ cũng cấp các interface cho PrintPage để lấy thông tin và yêu cầu interface từ PrintModel để lấy dữ liệu
- PrintModel component:
  - Chứa các component Printer(Quản lí máy in) và File(Quản lí tập tin)
  - Các component trong PrintModel sẽ cung cấp các interface cho PrintViewModel  để lấy thông tin danh sách máy in và các file tài liệu được tải lên
  - Interface của Database cũng được cung cấp để PrintModel thực hiện truy xuất trực tiếp thông tin trên cơ sở dữ liệu


