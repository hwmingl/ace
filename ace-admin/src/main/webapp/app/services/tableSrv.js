
copyright.value('baseTableService', {
    getCustomizedTable: function (scope) {
        function table(scope) {
            this.displayedDataList = scope.dataList;
            this.total = scope.totalRecords;
            if(scope.condition==null){
                scope.condition=new Object();
            }
            if(scope.condition.pageNumber==null){
                scope.condition.pageNumber=1;
            }
            if(scope.condition.pageSize==null){
                scope.condition.pageSize=20;
            }
            this.selectedRecordPerPage = parseInt(scope.condition.pageSize);
            this.currentPage = parseInt(scope.condition.pageNumber);
            this.startIndex = 1;
            this.endIndex = Math.min(this.selectedRecordPerPage, this.total);
            this.pageNumber = Math.ceil(this.total / this.selectedRecordPerPage);
            this.maxPageNumDisplayed = 5;
            this.query = '';

            this.updateStartIndex = function () {
                this.startIndex = (this.currentPage - 1) * this.selectedRecordPerPage + 1;
            };
            this.updateEndIndex = function () {
                this.endIndex = Math.min(this.startIndex + this.selectedRecordPerPage - 1, this.total);
            };
            this.updatePageNumber = function () {
                this.pageNumber = Math.ceil(this.total / this.selectedRecordPerPage);
            };
            this.updateIndexes = function () {
                this.updateStartIndex();
                this.updateEndIndex();
            };

            this.getCurrentPageNumDisplay = function () {
                var array = [];
                if (this.total == 0) {
                    return array
                }
                for (var i = Math.max(this.currentPage - 2, 1); i <= Math.min(this.pageNumber, this.currentPage + 2); i++) {
                    array.push(i);
                }
                var maxLength = Math.min(this.maxPageNumDisplayed, this.pageNumber);
                if (array.length < maxLength) {
                    if (array[0] === 1) {
                        for (var i = 1; i <= maxLength - array.length + 1; i++) {
                            array.push(array[array.length - 1] + 1);
                        }
                    }
                    else {
                        for (var i = 1; i <= maxLength - array.length + 1; i++) {
                            array.unshift(array[0] - 1);
                        }
                    }
                }
                return array;
            };
            this.getActiveLabel = function (pageNum) {
                if (this.currentPage === pageNum) {
                    return 'active';
                }
            };
            this.changePage = function (pageNum) {
                this.currentPage = pageNum;
                scope.condition.pageNumber=this.currentPage;
                scope.search();
            };
            this.previousPage = function () {
                if (this.currentPage > 1) {
                    this.currentPage = this.currentPage - 1;
                    scope.condition.pageNumber=this.currentPage;
                    scope.search();
                }
            };
            this.nextPage = function () {
                if (this.currentPage < this.pageNumber) {
                    this.currentPage = this.currentPage + 1;
                    scope.condition.pageNumber=this.currentPage;
                    scope.search();
                }
            };
            this.getDisableLabelforPrevious = function () {
                if (this.total === 0 || this.currentPage === 1) {
                    return 'disabled';
                }
            };
            this.getDisableLabelforNext = function () {
                if (this.total === 0 || this.currentPage === this.pageNumber) {
                    return 'disabled';
                }
            };
            this.gotoFirstPage = function () {
                this.currentPage = 1;
                scope.condition.pageNumber=this.currentPage;
                scope.search();
            };
            this.gotoLastPage = function () {
                this.currentPage = this.pageNumber;
                scope.condition.pageNumber=this.currentPage;
                scope.search();
            };
            scope.$watch('table.currentPage', function () {
                scope.table.updateIndexes();
            });

            scope.$watch('table.total', function () {
                if (scope.table.total == 0) {
                    scope.table.startIndex = 0;
                    scope.table.endIndex = 0;
                } else {
                    scope.table.updateIndexes();
                    scope.table.updatePageNumber();
                }
            });

            scope.$watchCollection('dataList', function () {
                scope.table.displayedDataList = scope.dataList;
                scope.table.total = scope.totalRecords;
            });
        }
        return new table(scope);
    }
});


