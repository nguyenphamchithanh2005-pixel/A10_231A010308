Mô tả báo cáo

NC2: Đổi GridLayoutManager thành StaggeredGridLayoutManager(2, VERTICAL) và cho mỗi ô chiều cao ảnh khác nhau, nên lưới so le kiểu Pinterest. 
Adapter giữ nguyên logic, chỉ đổi LayoutManager.

NC4: Đổi int[] thành List<Integer>. 
Giữ lâu một ô thì xóa khỏi danh sách, gọi notifyItemRemoved(viTri) nên các ô sau trượt lên có hiệu ứng. 
Danh sách hiện tại được gửi sang ViewerActivity nên ảnh đã xóa không còn trong màn hình xem lớn.
